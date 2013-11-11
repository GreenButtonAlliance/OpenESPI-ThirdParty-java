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

import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.Configuration;
import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.DataCustodianService;
import org.energyos.espi.thirdparty.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.security.Principal;
import java.util.Arrays;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class ScopeSelectionController extends BaseController {

    @Autowired
    private DataCustodianService dataCustodianService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("stateService")
    private StateService stateService;

    @Autowired
    @Qualifier("THIRD_PARTY_URL")
    private String thirdPartyURL;

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.GET)
    public String scopeSelection(@RequestParam("scope") String [] scopes, ModelMap model) throws JAXBException {
        model.put("scopeList", Arrays.asList(scopes));

        return "/RetailCustomer/ScopeSelection";
    }

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN_WITH_RETAIL_CUSTOMER_ID, method = RequestMethod.POST)
    public String scopeSelection(@RequestParam("Data_custodian_URL") String dataCustodianURL) throws JAXBException {
        return "redirect:" + dataCustodianURL + "?" + newScopeParams(Configuration.SCOPES) + "&ThirdPartyID=" + Configuration.THIRD_PARTY_CLIENT_ID;
    }

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.POST)
    public String scopeAuthorization(@RequestParam("scope") String scope, @RequestParam("DataCustodianID") String dataCustodianId, Principal principal) throws JAXBException {
        DataCustodian dataCustodian = dataCustodianService.findByClientId(dataCustodianId);

        Authorization authorization = new Authorization();

        authorization.setDataCustodian(dataCustodian);
        authorization.setThirdParty(Configuration.THIRD_PARTY_CLIENT_ID);
        authorization.setAuthorizationServer(dataCustodian.getUrl());
        authorization.setRetailCustomer(currentCustomer(principal));
        authorization.setState(stateService.newState());

        authorizationService.persist(authorization);

        return "redirect:" + dataCustodian.getUrl() + Routes.AUTHORIZATION_SERVER_AUTHORIZATION_ENDPOINT + "?client_id=" + Configuration.THIRD_PARTY_CLIENT_ID +
                "&redirect_uri=" + thirdPartyURL + Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK +
                "&response_type=code&scope=" + scope + "&state=" + authorization.getState();

    }

    public void setDataCustodianService(DataCustodianService dataCustodianService) {
        this.dataCustodianService = dataCustodianService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public void setThirdPartyURL(String thirdPartyURL) {
        this.thirdPartyURL = thirdPartyURL;
    }

    private String newScopeParams(String[] scopes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < scopes.length; i++) {
            if(i > 0)
                sb.append("&");
            sb.append("scope=" + scopes[i]);
        }
        return sb.toString();
    }
}
