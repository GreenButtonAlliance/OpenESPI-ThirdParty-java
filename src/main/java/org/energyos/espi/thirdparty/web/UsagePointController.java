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

import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/usagepoints")
public class UsagePointController {

    @Autowired
    private RetailCustomerService retailCustomerService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "usagepoints";
    }

    @RequestMapping(value = "/feed", method = RequestMethod.GET, produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseBody
    public String feed() {

/////////////////////////////////
//        String url = "http://datacustodian-dev.herokuapp.com/DataCustodian/RetailCustomer/{RetailCustomerId}/UsagePoint";
//        Map<String, String> vars = new HashMap<String, String>();
//        vars.put("RetailCustomerId", "6");
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(url, String.class, vars);
//
//        System.out.println("\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!\n" + result);
//        return result;
/////////////////////////////////

        return retailCustomerService.getUsagePoints();
    }

    public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
        this.retailCustomerService = retailCustomerService;
    }
}