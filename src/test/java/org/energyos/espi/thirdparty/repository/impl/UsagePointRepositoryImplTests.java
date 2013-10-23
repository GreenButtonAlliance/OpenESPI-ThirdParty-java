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

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class UsagePointRepositoryImplTests {

    @Autowired
    private UsagePointRepository repository;

    @Autowired
    private RetailCustomerService retailCustomerService;

    public RetailCustomer customer;

    public UsagePoint usagePoint;

    @Before
    public void before() {
        customer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(customer);
        usagePoint = EspiFactory.newUsagePoint(customer);

        repository.persist(usagePoint);
    }

    @Test
    public void persist() throws JAXBException {
        assertNotNull(usagePoint.getId());
    }

    @Test
    public void findAllByRetailCustomerId() {
        assertEquals(usagePoint.getId(), repository.findAllByRetailCustomerId(customer.getId()).get(0).getId());
    }

    @Test
    public void findById() {
        assertEquals(usagePoint.getId(), repository.findById(usagePoint.getId()).getId());
    }

    @Test
    public void findByUUID() {
        assertEquals(usagePoint.getId(), repository.findByUUID(usagePoint.getUUID()).getId());
    }
}
