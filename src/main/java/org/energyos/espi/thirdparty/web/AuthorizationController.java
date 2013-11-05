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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Controller
public class AuthorizationController extends BaseController {

    @Autowired
    private AuthorizationService service;

    @Autowired
    @Qualifier("THIRD_PARTY_URL")
    private String thirdPartyURL;

    @Autowired
    @Qualifier("clientRestTemplate")
    private RestTemplate template;

    @RequestMapping(value = Routes.ThirdPartyOAuthCodeCallbackURL, method = RequestMethod.GET)
    public String authorization(String code, String state, ModelMap model, Principal principal) {
        Authorization authorization = service.findByState(state);
        authorization.setAuthorizationServer(Routes.AuthorizationServerAuthorizationEndpoint);
        authorization.setThirdParty(Configuration.THIRD_PARTY_CLIENT_ID);

        DataCustodian dataCustodian = authorization.getDataCustodian();

        String url = String.format("%s%s?redirect_uri=%s&code=%s&grant_type=authorization_code", dataCustodian.getUrl(),
                Routes.AuthorizationServerTokenEndpoint, thirdPartyURL + Routes.ThirdPartyOAuthCodeCallbackURL, code);

        try {
            AccessToken token = template.getForObject(url, AccessToken.class);
            authorization.setAccessToken(token.getAccessToken());
            authorization.setSubscriptionURI(token.getResourceURI());
        } catch (HttpClientErrorException x) {
            throw new UserDeniedAuthorizationException("Unable to retrieve OAuth token", x);
        }

        service.merge(authorization);

        model.put("authorizationList", service.findAllByRetailCustomerId(currentCustomer(principal).getId()));

        return "/RetailCustomer/AuthorizationList/index";
    }

    @RequestMapping(value = Routes.ThirdPartyAuthorizationURL, method = RequestMethod.GET)
    public String index(ModelMap model, Authentication principal) {
        model.put("authorizationList", service.findAllByRetailCustomerId(currentCustomer(principal).getId()));
        return "/RetailCustomer/AuthorizationList/index";
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setThirdPartyURL(String thirdPartyURL) {
        this.thirdPartyURL = thirdPartyURL;
    }

    public void setService(AuthorizationService service) {
        this.service = service;
    }
}
