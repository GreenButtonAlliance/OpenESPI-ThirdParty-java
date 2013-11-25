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
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.StateService;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class AuthorizationTests {

    private MockMvc mockMvc;
    protected TestingAuthenticationToken authentication;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected RetailCustomerService retailCustomerService;

    @Autowired
    protected AuthorizationService authorizationService;

    @Autowired
    protected StateService stateService;

    @Autowired
    protected ApplicationInformationService applicationInformationService;

    private RetailCustomer retailCustomer;
    private Authorization authorization;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();

        retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomerService.persist(retailCustomer);
        authentication = new TestingAuthenticationToken(retailCustomer, null);

        ApplicationInformation dataCustodian = EspiFactory.newApplicationInformation();
        applicationInformationService.persist(dataCustodian);
        authorization = EspiFactory.newAuthorization(retailCustomer, dataCustodian);
        authorization.setState(stateService.newState());
        authorizationService.persist(authorization);
    }

    @Test
    public void authorization_returnsOKStatus() throws Exception {
        mockMvc.perform(get(Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK).principal(authentication)
                .param("code", "code").param("state", authorization.getState()))
                .andExpect(status().isOk());
    }

    @Test
    public void authorization_setsAuthorizationListModel() throws Exception {
        mockMvc.perform(get(Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK).principal(authentication)
                .param("code", "code").param("state", authorization.getState()))
                .andExpect(model().attributeExists("authorizationList"));
    }

    @Test
    public void index_setsAuthorizationListModel() throws Exception {
        mockMvc.perform(get(Routes.buildThirdPartyAuthorization("1")).principal(authentication))
                .andExpect(model().attributeExists("authorizationList"));
    }

    @Test
    public void index_returnsOKStatus() throws Exception {
        mockMvc.perform(get(Routes.buildThirdPartyAuthorization("1")).principal(authentication))
                .andExpect(status().isOk());
    }
}
