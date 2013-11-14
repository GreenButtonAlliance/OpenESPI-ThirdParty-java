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

package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.Configuration;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.BaseTest;
import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.DataCustodianService;
import org.energyos.espi.thirdparty.service.StateService;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScopeSelectionControllerTests extends BaseTest {

    private ScopeSelectionController controller;
    @Mock private DataCustodianService dataCustodianService;
    @Mock private AuthorizationService authorizationService;
    @Mock private StateService stateService;

    @Before
    public void before() {
        controller = new ScopeSelectionController();
        controller.setThirdPartyURL(Configuration.THIRD_PARTY_BASE_URL);
        controller.setDataCustodianService(dataCustodianService);
        controller.setAuthorizationService(authorizationService);
        controller.setStateService(stateService);
    }

    @Test
    public void post_scopeSelection_redirects() throws Exception {
        String url = "DataCustodianURL";

        String redirectURL = controller.scopeSelection(url);

        assertEquals(String.format("redirect:%s?scope=%s&scope=%s&ThirdPartyID=%s", url, Configuration.SCOPES[0], Configuration.SCOPES[1],
                Configuration.THIRD_PARTY_CLIENT_ID), redirectURL);
    }

    @Test
    public void get_scopeSelection_displaysScopeSelectionView() throws Exception {
        ScopeSelectionController controller = new ScopeSelectionController();

        assertEquals("/RetailCustomer/ScopeSelection", controller.scopeSelection(new String [] {}, new ModelMap()));
    }

    @Test
    public void get_scopeSelection_setsScopeListModel() throws Exception {
        ModelMap model = new ModelMap();

        controller.scopeSelection(new String [] {"scope1", "scope2"}, model);

        assertTrue(((List<String>)model.get("scopeList")).size() > 0);
    }

    @Test
    public void post_scopeAuthorization_redirects() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        when(dataCustodianService.findByClientId(eq(dataCustodian.getClientId()))).thenReturn(dataCustodian);

        String expectedRedirectURL = String.format("redirect:%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s&state=",
                dataCustodian.getUrl() + Routes.AUTHORIZATION_SERVER_AUTHORIZATION_ENDPOINT,
                Configuration.THIRD_PARTY_CLIENT_ID,
                Configuration.THIRD_PARTY_BASE_URL + Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK,
                "code",
                Configuration.SCOPES[0]);

        Authentication principal = mock(Authentication.class);
        when(principal.getPrincipal()).thenReturn(EspiFactory.newRetailCustomer());

        assertTrue(controller.scopeAuthorization(Configuration.SCOPES[0], dataCustodian.getClientId(), principal).startsWith(expectedRedirectURL));
    }

    @Test
    public void post_scopeAuthorization_createsAuthorization() throws Exception {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        when(dataCustodianService.findByClientId(eq(dataCustodian.getClientId()))).thenReturn(dataCustodian);

        AuthorizationService authorizationService = mock(AuthorizationService.class);
        controller.setAuthorizationService(authorizationService);

        Authentication principal = mock(Authentication.class);
        when(principal.getPrincipal()).thenReturn(EspiFactory.newRetailCustomer());

        controller.scopeAuthorization(Configuration.SCOPES[0], dataCustodian.getClientId(), principal);

        verify(authorizationService).persist(any(Authorization.class));
    }
}
