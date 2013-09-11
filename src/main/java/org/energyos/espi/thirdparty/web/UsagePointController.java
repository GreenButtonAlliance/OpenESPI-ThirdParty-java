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

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBException;

@Controller
@RequestMapping("/usagepoints")
public class UsagePointController {

    @Autowired
    private UsagePointService usagePointService;

    @ModelAttribute
    public java.util.List<UsagePoint> usagePoints() throws JAXBException {
        RetailCustomer customer = new RetailCustomer();
        customer.setId(1L);

        return usagePointService.findAllByRetailCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/usagepoints/index";
    }

    public void setUsagePointService(UsagePointService usagePointService) {
        this.usagePointService = usagePointService;
    }

    @RequestMapping(value = "{usagePointId}/show", method = RequestMethod.GET)
    public String show(@PathVariable String usagePointId, ModelMap model) throws JAXBException {
        model.put("usagePoint", usagePointService.findById(usagePointId));
        return "/usagepoints/show";
    }
}
