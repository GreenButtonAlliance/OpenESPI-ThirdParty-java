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

package org.energyos.espi.thirdparty.integration.web;

import org.energyos.espi.thirdparty.domain.Configuration;
import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.domain.Routes;
import org.energyos.espi.thirdparty.service.DataCustodianService;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Profile("test")
public class ScopeSelectionTests {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected DataCustodianService service;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void post_scopeSelection_returnsRedirectStatus() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        service.persist(dataCustodian);

        mockMvc.perform(post("/RetailCustomer/1/ScopeSelection")
                .param("Data_custodian", dataCustodian.getId().toString())
                .param("Data_custodian_URL", dataCustodian.getUrl()))
               .andExpect(status().is(302));
    }

    @Test
    public void post_scopeSelection_redirectsToDataCustodian() throws Exception {
        String redirectURL = "http://localhost:8080/DataCustodian/ScopeSelection";

        mockMvc.perform(post("/RetailCustomer/1/ScopeSelection")
                .param("Data_custodian", "1")
                .param("Data_custodian_URL", redirectURL))
                .andExpect(redirectedUrl(String.format("%s?scope=%s&scope=%s&ThirdPartyID=%s", redirectURL,
                        "FB=4,5,15 IntervalDuration=3600 BlockDuration=monthly HistoryLength=13",
                        "FB=4,5,12,15,16 IntervalDuration=monthly BlockDuration=monthly HistoryLength=13",
                        Configuration.THIRD_PARTY_CLIENT_ID)));
    }

    @Test
    public void get_scopeSelection_returnsOkStatus() throws Exception {
        mockMvc.perform(get(Routes.ThirdPartyScopeSelectionScreen).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(status().isOk());
    }

    @Test
    public void get_scopeSelection_displaysScopeSelectionView() throws Exception {
        mockMvc.perform(get(Routes.ThirdPartyScopeSelectionScreen).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(view().name("/RetailCustomer/ScopeSelection"));
    }

    @Test
    public void get_scopeSelection_setsScopeListModel() throws Exception {
        mockMvc.perform(get(Routes.ThirdPartyScopeSelectionScreen).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(model().attributeExists("scopeList"));
    }
}