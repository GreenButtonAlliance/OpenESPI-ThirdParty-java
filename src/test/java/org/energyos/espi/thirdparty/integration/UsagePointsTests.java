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

package org.energyos.espi.thirdparty.integration;

import junit.framework.Assert;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.energyos.espi.thirdparty.service.UsagePointService;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.energyos.espi.thirdparty.utils.factories.EspiPersistenceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Profile("test")
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
        retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);
        usagePoint = EspiFactory.newUsagePoint(retailCustomer);
        usagePointService.persist(usagePoint);
        Assert.assertNotNull(usagePoint.getId());

        this.mockMvc = webAppContextSetup(this.wac).build();
        authentication = new TestingAuthenticationToken(retailCustomer, null);
        espiPersistenceFactory.createAuthorization(retailCustomer);
    }

    @Test
    public void index_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    public void index_displaysIndexView() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(view().name("/usagepoints/index"));
    }

    @Test
    public void index_setsUsagePointListModel() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/show")
                .principal(authentication))
                .andExpect(model().attributeExists("usagePointList"));
    }

    @Test
    public void show_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/" + usagePoint.getHashedId() + "/show")
                .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    public void show_displaysShowView() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/" + usagePoint.getHashedId() + "/show")
                .principal(authentication))
                .andExpect(view().name("/usagepoints/show"));
    }

    @Test
    public void show_setsUsagePointModel() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + retailCustomer.getHashedId() + "/UsagePoint/7bc41774-7190-4864-841c-861ac76d46c2/show")
                .principal(authentication))
                .andExpect(model().attributeExists("usagePoint"));
    }
}