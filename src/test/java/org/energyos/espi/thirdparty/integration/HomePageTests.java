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

package org.energyos.espi.thirdparty.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.service.RetailCustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class HomePageTests {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected RetailCustomerService retailCustomerService;
    private TestingAuthenticationToken authentication;
    private RetailCustomer customer;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        customer = new RetailCustomer();
        customer.setId(1L);
        authentication = new TestingAuthenticationToken(customer, null);
    }

    @Test
    public void index_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void index_whenNotLoggedIn_displaysHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
    }

    @Test
    public void index_whenLoggedIn_redirectsToRetailCustomerHome() throws Exception {
        mockMvc.perform(get("/").principal(authentication))
                .andExpect(redirectedUrl("/RetailCustomer/" + customer.getId() + "/home"));
    }

    @Test
    public void home_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    public void home_whenNotLoggedIn_displaysHomeView() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(view().name("home"));
    }

    @Test
    public void home_whenLoggedIn_redirectsToRetailCustomerHome() throws Exception {
        mockMvc.perform(get("/home").principal(authentication))
                .andExpect(redirectedUrl("/RetailCustomer/" + customer.getId() + "/home"));
    }

    @Test
    public void termsOfService_displaysTermsOfServiceView() throws Exception {
        mockMvc.perform(get("/TermsOfService"))
            .andExpect(view().name("/TermsOfService"));
    }

    @Test
    public void usagePolicy_displaysUsagePolicyView() throws Exception {
        mockMvc.perform(get("/UsagePolicy"))
                .andExpect(view().name("/UsagePolicy"));
    }
}