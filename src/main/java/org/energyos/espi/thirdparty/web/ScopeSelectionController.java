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

import org.energyos.espi.thirdparty.domain.Configuration;
import org.energyos.espi.thirdparty.domain.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

@Controller
public class ScopeSelectionController {

    @RequestMapping(value = Routes.ThirdPartyScopeSelectionScreen, method = RequestMethod.GET)
    public String scopeSelection(@RequestParam("scope") String [] scopes, ModelMap model) throws JAXBException {
        model.put("scopeList", Arrays.asList(scopes));

        return "/RetailCustomer/ScopeSelection";
    }

    @RequestMapping(value = "/RetailCustomer/{retailCustomerId}/ScopeSelection", method = RequestMethod.POST)
    public String scopeSelection(@PathVariable String retailCustomerId, @RequestParam("Data_custodian") Long dataCustodianId, @RequestParam("Data_custodian_URL") String dataCustodianURL) throws JAXBException {
        return "redirect:" + dataCustodianURL + "?" + newScopeParams(Configuration.SCOPES) + "&ThirdPartyID=" + Configuration.THIRD_PARTY_CLIENT_ID;
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
