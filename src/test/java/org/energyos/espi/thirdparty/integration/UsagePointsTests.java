/*
 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.thirdparty.integration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.test.EspiPersistenceFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Profile("test")
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class UsagePointsTests {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private RetailCustomerService retailCustomerService;
    @Autowired
    private UsagePointService usagePointService;
    @Autowired
    private EspiPersistenceFactory espiPersistenceFactory;

    private MockMvc mockMvc;
    private TestingAuthenticationToken authentication;
    public RetailCustomer retailCustomer;
    public UsagePoint usagePoint;

    @Before
    public void setup() {
        retailCustomer = espiPersistenceFactory.createRetailCustomer();
        usagePoint = EspiFactory.newUsagePoint(retailCustomer);
        usagePointService.persist(usagePoint);
        assertThat(usagePoint.getId(), is(notNullValue()));

        this.mockMvc = webAppContextSetup(this.wac).build();
        authentication = new TestingAuthenticationToken(retailCustomer, null);
        espiPersistenceFactory.createAuthorization(retailCustomer);
    }

    @Test
    @Ignore
    public void index_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void index_displaysIndexView() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(view().name("/usagepoints/index"));
    }

    @Test
    @Ignore
    public void index_setsUsagePointListModel() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(model().attributeExists("usagePointList"));
    }

    @Test
    @Ignore
    public void show_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/" + usagePoint.getHashedId() + "/show")
                .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void show_displaysShowView() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/" + usagePoint.getHashedId() + "/show")
                .principal(authentication))
                .andExpect(view().name("/usagepoints/show"));
    }

    @Test
    @Ignore
    public void show_setsUsagePointModel() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/48C2A019-5598-4E16-B0F9-49E4FF27F5FB/show")
                .principal(authentication))
                .andExpect(model().attributeExists("usagePoint"));
    }
}