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

import java.security.Principal;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.service.MeterReadingRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MeterReadingController extends BaseController {
    @Autowired
    private MeterReadingRESTService meterReadingService;

    @RequestMapping(value = Routes.THIRD_PARTY_METER_READINGS_SHOW, method = RequestMethod.GET)
    public String show(@PathVariable String meterReadingId, ModelMap model, Principal principal) throws JAXBException {
        RetailCustomer currentCustomer = currentCustomer(principal);
        model.put("meterReading", meterReadingService.findByUUID(currentCustomer.getId(), UUID.fromString(meterReadingId)));
        return "/meterreadings/show";
    }

    public void setMeterReadingService(MeterReadingRESTService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }
}
