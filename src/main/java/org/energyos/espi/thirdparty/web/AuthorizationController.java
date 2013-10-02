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
import org.energyos.espi.thirdparty.domain.Configuration;
import org.energyos.espi.thirdparty.domain.Routes;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Controller
public class AuthorizationController extends BaseController {

    @Autowired
    private AuthorizationService service;

    @Autowired
    @Qualifier("clientRestTemplate")
    private RestTemplate template;

    @RequestMapping(value = Routes.ThirdPartyOAuthCodeCallbackURL, method = RequestMethod.GET)
    public String authorization(String code, ModelMap model, Principal principal) {
        String url = String.format("http://localhost:8080/DataCustodian%s?redirect_uri=%s&code=%s&grant_type=authorization_code",
                Routes.AuthorizationServerTokenEndpoint, "http://localhost:8080/ThirdParty" + Routes.ThirdPartyOAuthCodeCallbackURL, code);

        String token = template.getForObject(url, String.class);

        Authorization authorization = new Authorization();
        authorization.setAccessToken(token);
        authorization.setAuthorizationServer(Routes.AuthorizationServerAuthorizationEndpoint);
        authorization.setThirdParty(Configuration.THIRD_PARTY_CLIENT_ID);
        authorization.setRetailCustomer(currentCustomer(principal));

        service.persist(authorization);

        model.put("authorizationList", service.findAllByRetailCustomerId(currentCustomer(principal).getId()));

        return null;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setService(AuthorizationService service) {
        this.service = service;
    }
}