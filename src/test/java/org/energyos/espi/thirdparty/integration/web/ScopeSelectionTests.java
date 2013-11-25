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

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.StateService;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.thirdparty.web.ScopeSelectionController;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Profile("test")
@Transactional
public class ScopeSelectionTests {

    private MockMvc mockMvc;
    protected TestingAuthenticationToken authentication;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected ApplicationInformationService service;

    @Autowired
    protected RetailCustomerService retailCustomerService;

    @Autowired
    protected StateService stateService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        RetailCustomer customer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(customer);
        authentication = new TestingAuthenticationToken(customer, null);
    }

    @Test
    public void post_scopeSelection_returnsRedirectStatus() throws Exception {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        service.persist(applicationInformation);

        mockMvc.perform(post("/RetailCustomer/1/ScopeSelection")
                .param("Data_custodian", applicationInformation.getDataCustodianId())
                .param("Data_custodian_URL", applicationInformation.getDataCustodianAuthorizationResource()))
               .andExpect(status().is(302));
    }

    @Test
    public void post_scopeSelection_redirectsToDataCustodian() throws Exception {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        service.persist(applicationInformation);

        String redirectURL = "http://localhost:8080/DataCustodian/ScopeSelection";

        mockMvc.perform(post("/RetailCustomer/1/ScopeSelection")
                .param("Data_custodian", applicationInformation.getDataCustodianId())
                .param("Data_custodian_URL", redirectURL))
                .andExpect(redirectedUrl(String.format("%s?scope=%s&scope=%s&ThirdPartyID=%s", redirectURL,
                        ScopeSelectionController.THIRD_PARTY_SCOPES[0],
                        ScopeSelectionController.THIRD_PARTY_SCOPES[1],
                        applicationInformation.getDataCustodianThirdPartyId())));
    }

    @Test
    public void get_scopeSelection_returnsOkStatus() throws Exception {
        mockMvc.perform(get(Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(status().isOk());
    }

    @Test
    public void get_scopeSelection_displaysScopeSelectionView() throws Exception {
        mockMvc.perform(get(Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(view().name("/RetailCustomer/ScopeSelection"));
    }

    @Test
    public void get_scopeSelection_setsScopeListModel() throws Exception {
        mockMvc.perform(get(Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN).param("scope", "scope1").param("scope", "scope2"))
                .andExpect(model().attributeExists("scopeList"));
    }

    @Test
    public void post_scopeAuthorization_returnsRedirectStatus() throws Exception {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        service.persist(applicationInformation);
        String scope = ScopeSelectionController.THIRD_PARTY_SCOPES[0];

        mockMvc.perform(post(Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN).principal(authentication)
                .param("scope", scope).param("DataCustodianID", applicationInformation.getDataCustodianId()))
                .andExpect(status().is(302));
    }

    @Test
    public void post_scopeAuthorization_redirectsToDataCustodian() throws Exception {
        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();
        service.persist(applicationInformation);

        String redirectURL = String.format("%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s",
                applicationInformation.getDataCustodianAuthorizationResource(), applicationInformation.getDataCustodianThirdPartyId(),
                 "http://localhost:8080/ThirdParty" + Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK, "code",
                ScopeSelectionController.THIRD_PARTY_SCOPES[0], stateService.newState());

        mockMvc.perform(post(Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN).principal(authentication)
                .param("scope", ScopeSelectionController.THIRD_PARTY_SCOPES[0]).param("DataCustodianID", applicationInformation.getDataCustodianId()))
                .andExpect(redirectedUrl(redirectURL));
    }
}