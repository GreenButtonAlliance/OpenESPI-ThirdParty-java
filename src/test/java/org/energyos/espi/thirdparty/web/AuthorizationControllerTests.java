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

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.Routes;
import org.energyos.espi.thirdparty.service.AuthorizationService;
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
import static org.mockito.Mockito.*;

public class AuthorizationControllerTests {

    private AuthorizationController controller;
    private RestTemplate restTemplate;
    private Authentication principal;
    private AuthorizationService service;

    @Before
    public void before() {
        controller = new AuthorizationController();

        service = mock(AuthorizationService.class);
        controller.setService(service);

        restTemplate = mock(RestTemplate.class);
        controller.setTemplate(restTemplate);

        RetailCustomer retailCustomer = new RetailCustomer();
        principal = mock(Authentication.class);
        when(principal.getPrincipal()).thenReturn(retailCustomer);
    }

    @Test
    public void authorization_fetchesToken() throws Exception {
        String url = String.format("http://localhost:8080/DataCustodian%s?redirect_uri=%s&code=%s&grant_type=authorization_code",
                Routes.AuthorizationServerTokenEndpoint, "http://localhost:8080/ThirdParty" + Routes.ThirdPartyOAuthCodeCallbackURL, "code");

        controller.authorization("code", new ModelMap(), principal);

        verify(restTemplate).getForObject(eq(url), eq(String.class));
    }

    @Test
    public void authorization_savesAuthorization() throws Exception {
        String url = String.format("http://localhost:8080/DataCustodian%s?redirect_uri=%s&code=%s&grant_type=authorization_code",
                Routes.AuthorizationServerTokenEndpoint, "http://localhost:8080/ThirdParty" + Routes.ThirdPartyOAuthCodeCallbackURL, "code");

        controller.authorization("code", new ModelMap(), principal);

        verify(service).persist(any(Authorization.class));
    }

    @Test
    public void authorization_returnsAuthorizationList() throws Exception {
        List<Authorization> authorizations = new ArrayList<Authorization>();
        authorizations.add(new Authorization());
        when(service.findAllByRetailCustomerId(anyLong())).thenReturn(authorizations);
        ModelMap model = new ModelMap();

        controller.authorization("code", model, principal);

        assertEquals(authorizations, model.get("authorizationList"));
    }
}
