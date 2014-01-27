/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.thirdparty.repository.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.ServiceCategory;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class ResourceRepositoryImplTests {

    @Autowired
    public ResourceRepository repository;

    @Autowired
    private RetailCustomerRepository retailCustomerRepository;

    public UsagePoint originalResource;

    public UsagePoint updatedResource;

    @Before
    public void before() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);

        originalResource = EspiFactory.newUsagePoint(retailCustomer);
        repository.persist(originalResource);

        updatedResource = new UsagePoint();
        updatedResource.setUUID(originalResource.getUUID());
        updatedResource.setServiceCategory(new ServiceCategory(ServiceCategory.REFUSE_WASTER_SERVICE));
        updatedResource.setStatus(Short.MAX_VALUE);
        updatedResource.setDescription("Changed");
        updatedResource.setRoleFlags("Changed".getBytes());
    }

    @Test
    public void persist_savesResource() {
        assertNotNull(originalResource.getId());
    }

    @Test
    public void update_updatesResource() {
        repository.update(updatedResource);

        UsagePoint up = repository.findByUUID(originalResource.getUUID());
        assertEquals("Changed", up.getDescription());
    }

    @Test
    public void update_preservesId() {
        repository.update(updatedResource);

        UsagePoint up = repository.findByUUID(originalResource.getUUID());
        assertEquals(originalResource.getId(), up.getId());
    }

    @Test
    public void update_updatesProperties() throws JAXBException {
        repository.update(updatedResource);

        UsagePoint up = repository.findByUUID(originalResource.getUUID());
        assertEquals("Changed", up.getDescription());
        assertArrayEquals("Changed".getBytes(), up.getRoleFlags());
        assertEquals(ServiceCategory.REFUSE_WASTER_SERVICE, up.getServiceCategory().getKind());
        assertEquals(Long.valueOf(Short.MAX_VALUE), Long.valueOf(up.getStatus()));
    }

    @Test
    public void update_preservesRetailCustomer() throws JAXBException {
        repository.update(updatedResource);

        UsagePoint up = repository.findByUUID(originalResource.getUUID());
        assertThat(up.getRetailCustomer().getId(), is(not(nullValue())));
    }
}
