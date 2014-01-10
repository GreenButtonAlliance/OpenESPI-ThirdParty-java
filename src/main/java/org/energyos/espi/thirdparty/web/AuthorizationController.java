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

package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.AccessToken;
import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;

@Controller
public class AuthorizationController extends BaseController {

    @Autowired
    private AuthorizationService service;

    @Autowired
    @Qualifier("clientRestTemplateFactory")
    private ClientRestTemplateFactory templateFactory;
    
    //TODO: Need to be able to process an error response from the /oauth/authorization endpoint
    //      Since this is a redirect, the query string must be inspected to determine
    //      if an error response is returned

    @RequestMapping(value = Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK, method = RequestMethod.GET)
    public String authorization(String code, String state, ModelMap model, Principal principal) {
    	
    	//TODO: Need to be able to process an exception should the "state" in the /oauth/authorization response
    	//      not match the "state" value sent in the /oauth/authorization request
    	
        Authorization authorization = service.findByState(state);
        authorization.setThirdParty(authorization.getApplicationInformation().getClientId());

        ApplicationInformation applicationInformation = authorization.getApplicationInformation();

        String url = String.format("%s?redirect_uri=%s&code=%s&grant_type=authorization_code", applicationInformation.getAuthorizationServerTokenEndpoint(),
                applicationInformation.getRedirectUri(), code);

        try {
            ClientRestTemplate restTemplate = templateFactory.newClientRestTemplate(applicationInformation.getClientId(), applicationInformation.getClientSecret());
            AccessToken token = restTemplate.getForObject(url, AccessToken.class);
            authorization.setAccessToken(token.getAccessToken());
            
            authorization.setTokenType(token.getTokenType());
            authorization.setExpiresIn(token.getExpiresIn());
            authorization.setRefreshToken(token.getRefreshToken());
            authorization.setScope(token.getScope());
            authorization.setAuthorizationURI(token.getAuthorizationURI());
            authorization.setSubscriptionURI(token.getResourceURI());
        } catch (HttpClientErrorException x) {
            throw new UserDeniedAuthorizationException("Unable to retrieve OAuth token", x);
        }

        service.merge(authorization);

        model.put("authorizationList", service.findAllByRetailCustomerId(currentCustomer(principal).getId()));

        return "/RetailCustomer/AuthorizationList/index";
    }

    @RequestMapping(value = Routes.THIRD_PARTY_AUTHORIZATION, method = RequestMethod.GET)
    public String index(ModelMap model, Authentication principal) {
        model.put("authorizationList", service.findAllByRetailCustomerId(currentCustomer(principal).getId()));
        return "/RetailCustomer/AuthorizationList/index";
    }

    public void setService(AuthorizationService service) {
        this.service = service;
    }

    public void setTemplateFactory(ClientRestTemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
}
