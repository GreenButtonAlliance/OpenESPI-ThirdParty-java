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

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.StateService;
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
import java.util.UUID;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ScopeSelectionController extends BaseController {

    public static final String[] THIRD_PARTY_SCOPES = new String[]{
            "FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13",
            "FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"
    };

    @Autowired
    private ApplicationInformationService applicationInformationService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("stateService")
    private StateService stateService;

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.GET)
    public String scopeSelection(@RequestParam("scope") String [] scopes, ModelMap model) throws JAXBException {
        model.put("scopeList", Arrays.asList(scopes));

        return "/RetailCustomer/ScopeSelection";
    }

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN_WITH_RETAIL_CUSTOMER_ID, method = RequestMethod.POST)
    public String scopeSelection(@RequestParam("Data_custodian") String dataCustodianId, @RequestParam("Data_custodian_URL") String dataCustodianURL) throws JAXBException {
        ApplicationInformation applicationInformation = applicationInformationService.findByDataCustodianClientId(dataCustodianId);
        return "redirect:" + dataCustodianURL + "?" + newScopeParams(THIRD_PARTY_SCOPES) + "&ThirdPartyID=" + applicationInformation.getDataCustodianThirdPartyId();
    }

    @RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.POST)
    public String scopeAuthorization(@RequestParam("scope") String scope, @RequestParam("DataCustodianID") String dataCustodianId, Principal principal) throws JAXBException {
        ApplicationInformation applicationInformation = applicationInformationService.findByDataCustodianClientId(dataCustodianId);

        Authorization authorization = new Authorization();

        authorization.setApplicationInformation(applicationInformation);
        authorization.setThirdParty(applicationInformation.getDataCustodianThirdPartyId());
        authorization.setAuthorizationServer(applicationInformation.getDataCustodianDefaultScopeResource());
        authorization.setRetailCustomer(currentCustomer(principal));
        authorization.setState(stateService.newState());
        authorization.setUUID(UUID.randomUUID());

        authorizationService.persist(authorization);

        return "redirect:" + applicationInformation.getDataCustodianAuthorizationResource() +
                "?client_id=" + applicationInformation.getDataCustodianThirdPartyId() +
                "&redirect_uri=" + applicationInformation.getThirdPartyDefaultOAuthCallback() +
                "&response_type=code&scope=" + scope + "&state=" + authorization.getState();

    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public void setApplicationInformationService(ApplicationInformationService applicationInformationService) {
        this.applicationInformationService = applicationInformationService;
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
