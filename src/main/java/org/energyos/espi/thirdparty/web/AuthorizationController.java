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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.Map;

@Controller
public class AuthorizationController extends BaseController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("clientRestTemplateFactory")
    private ClientRestTemplateFactory templateFactory;

    @RequestMapping(value = Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK, method = RequestMethod.GET)
    public String authorization(String code, String state, ModelMap model, Principal principal, @RequestParam(value = "error", required = false) String error,
    		@RequestParam(value = "error_description", required = false) String error_description, @RequestParam(value = "error_uri", required = false) String error_uri) {
  	
    	//TODO: Add logic to handle when the /oauth/authorization response "state" element does not match the /oauth/authorization request "state" element   	
        Authorization authorization = authorizationService.findByState(state);        
        ApplicationInformation applicationInformation = authorization.getApplicationInformation();      

        // Verify /oauth/authorization Endpoint process completed successfully
        if(code != null) {
        	try {
            
        		// Update Authorization record with returned authorization code for audit purposes
        		authorization.setCode(code);
        		authorization.setGrantType("authorization_code");
        		authorizationService.merge(authorization);
        		
        		// Format /oauth/token Endpoint request
        		String url = String.format("%s?redirect_uri=%s&code=%s&grant_type=authorization_code", applicationInformation.getAuthorizationServerTokenEndpoint(),
        				applicationInformation.getRedirectUri(), code);        		
            
        		// Issue /oauth/token Endpoint request
        		ClientRestTemplate restTemplate = templateFactory.newClientRestTemplate(applicationInformation.getClientId(), applicationInformation.getClientSecret());
          
        		// Process /oauth/token Endpoint response
        		AccessToken token = restTemplate.getForObject(url, AccessToken.class);            
        		authorization.setAccessToken(token.getAccessToken());            
        		authorization.setTokenType(token.getTokenType());
        		authorization.setExpiresIn(token.getExpiresIn());
        		authorization.setRefreshToken(token.getRefreshToken());
        		authorization.setScope(token.getScope());
        		authorization.setAuthorizationURI(token.getAuthorizationURI());
        		authorization.setResourceURI(token.getResourceURI());
        		authorization.setSubscriptionURI(token.getResourceURI());
        		authorization.setStatus("1");   // Set authorization record status as "Active"
        		authorization.setState(null);	// Clear State as a security measure            

        		// Update authorization record with /oauth/token response data
        		authorizationService.merge(authorization);
            
        	} catch (HttpClientErrorException x) {
        		
        		//TODO: Figure out how to extract error, error_description and error_uri from JSON response
        		     		
        		// Update authorization record        	
            	authorization.setError(error);
            	authorization.setErrorDescription(error_description);
            	authorization.setErrorUri(error_uri);        		
        		authorization.setStatus("2");   // Set authorization record status as "Denied"
        		authorization.setState(null);	// Clear State as a security measure           
        		authorizationService.merge(authorization);
        	
        		//TODO: Process error response from /oauth/token Endpoint 
        		throw new UserDeniedAuthorizationException("Unable to retrieve OAuth token", x);
        	}
        }        	
        else {
                    	
        	// Update authorization record        	
        	authorization.setError(error);
        	authorization.setErrorDescription(error_description);
        	authorization.setErrorUri(error_uri);  
        	authorization.setStatus("2");   //Set authorization record status as "Denied"
        	authorization.setState(null);
        	authorizationService.merge(authorization);
        	
        	// Test for "access_denied" failure code 
        	if(error.equals("access_denied")) {
        		throw new UserDeniedAuthorizationException("User Denied Access");
        	}
        		
        }

        model.put("authorizationList", authorizationService.findAllByRetailCustomerId(currentCustomer(principal).getId()));

        return "/RetailCustomer/AuthorizationList/index";
    }

    @RequestMapping(value = Routes.THIRD_PARTY_AUTHORIZATION, method = RequestMethod.GET)
    public String index(ModelMap model, Authentication principal) {
        model.put("authorizationList", authorizationService.findAllByRetailCustomerId(currentCustomer(principal).getId()));
        return "/RetailCustomer/AuthorizationList/index";
    }

    public void setService(AuthorizationService service) {
        this.authorizationService = service;
    }

    public void setTemplateFactory(ClientRestTemplateFactory templateFactory) {
        this.templateFactory = templateFactory;
    }
}
