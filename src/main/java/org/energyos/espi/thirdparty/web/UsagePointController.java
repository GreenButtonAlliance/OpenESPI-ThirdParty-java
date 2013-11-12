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
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import javax.xml.bind.JAXBException;
import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class UsagePointController extends BaseController {

    @Autowired
    private UsagePointRESTRepository usagePointRESTRepository;

    @RequestMapping(value = Routes.USAGE_POINT_INDEX_TP, method = RequestMethod.GET)
    public String index(ModelMap model, Principal principal) throws JAXBException {
        RetailCustomer currentCustomer = currentCustomer(principal);
        try {
            List<UsagePoint> usagePointList = usagePointRESTRepository.findAllByRetailCustomerId(currentCustomer.getId());
            model.put("usagePointList", usagePointList);

            return "/usagepoints/index";
        } catch(IndexOutOfBoundsException x) {
            return "redirect:/RetailCustomer/" + currentCustomer.getHashedId() + "/DataCustodianList";
        } catch(HttpClientErrorException x) {
            return "redirect:/RetailCustomer/" + currentCustomer.getHashedId() + "/DataCustodianList";
        }
    }

    @RequestMapping(value = Routes.USAGE_POINT_SHOW_TP, method = RequestMethod.GET)
    public String show(@PathVariable("UsagePointHashedId") String usagePointHashedId, ModelMap model, Principal principal) throws JAXBException {
        RetailCustomer currentCustomer = currentCustomer(principal);
        model.put("usagePoint", usagePointRESTRepository.findByHashedId(currentCustomer.getId(), usagePointHashedId));

        return "/usagepoints/show";
    }

    public void setUsagePointRESTRepository(UsagePointRESTRepository usagePointRESTRepository) {
        this.usagePointRESTRepository = usagePointRESTRepository;
    }
}
