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

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.AuthorizationRepository;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class AuthorizationRepositoryImplTests {

    @Autowired
    AuthorizationRepository repository;

    @Autowired
    RetailCustomerRepository retailCustomerRepository;

    @Test
    @Transactional
    public void findAllByRetailCustomerId() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);
        repository.persist(EspiFactory.newAuthorization(retailCustomer));

        assertTrue(repository.findAllByRetailCustomerId(retailCustomer.getId()).size() == 1);
    }

    @Test
    @Transactional
    public void persist() {
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerRepository.persist(retailCustomer);
        Authorization authorization = EspiFactory.newAuthorization(retailCustomer);

        repository.persist(authorization);

        assertNotNull(authorization.getId());
    }
}
