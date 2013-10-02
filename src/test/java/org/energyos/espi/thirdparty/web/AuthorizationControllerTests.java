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

import org.energyos.espi.thirdparty.domain.*;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

public class AuthorizationControllerTests {
    private final String CODE = "code";

    private AuthorizationController controller;
    private RestTemplate restTemplate;
    private Authentication principal;
    private AuthorizationService service;
    private RetailCustomer retailCustomer;
    private DataCustodian dataCustodian;
    private Authorization authorization;

    @Before
    public void before() {
        controller = new AuthorizationController();
        controller.setThirdPartyURL(Configuration.THIRD_PARTY_BASE_URL);

        service = mock(AuthorizationService.class);
        controller.setService(service);

        restTemplate = mock(RestTemplate.class);
        controller.setTemplate(restTemplate);

        retailCustomer = EspiFactory.newRetailCustomer();
        principal = mock(Authentication.class);
        when(principal.getPrincipal()).thenReturn(retailCustomer);

        dataCustodian = EspiFactory.newDataCustodian();
        authorization = EspiFactory.newAuthorization(retailCustomer, dataCustodian);
        when(service.findByState(authorization.getState())).thenReturn(authorization);
        when(restTemplate.getForObject(anyString(), eq(AccessToken.class))).thenReturn(new AccessToken());
    }

    @Test
    public void authorization_fetchesToken() throws Exception {
        String url = String.format("%s%s?redirect_uri=%s&code=%s&grant_type=authorization_code",
                dataCustodian.getUrl(), Routes.AuthorizationServerTokenEndpoint,
                Configuration.THIRD_PARTY_BASE_URL + Routes.ThirdPartyOAuthCodeCallbackURL, CODE);

        controller.authorization(CODE, authorization.getState(), new ModelMap(), principal);

        verify(restTemplate).getForObject(eq(url), eq(AccessToken.class));
    }

    @Test
    public void authorization_updatesAuthorization() throws Exception {
        controller.authorization(CODE, authorization.getState(), new ModelMap(), principal);

        verify(service).merge(any(Authorization.class));
    }

    @Test
    public void authorization_returnsAuthorizationList() throws Exception {
        List<Authorization> authorizations = new ArrayList<Authorization>();
        authorizations.add(new Authorization());
        when(service.findAllByRetailCustomerId(anyLong())).thenReturn(authorizations);
        ModelMap model = new ModelMap();

        controller.authorization(CODE, authorization.getState(), model, principal);

        assertEquals(authorizations, model.get("authorizationList"));
    }
}
