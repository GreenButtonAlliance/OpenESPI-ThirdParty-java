package org.energyos.espi.thirdparty.integration;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.test.EspiPersistenceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional
public class MeterReadingTests {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private EspiPersistenceFactory espiPersistenceFactory;
    private TestingAuthenticationToken authentication;


    private static final String UUID = "E8B19EF0-6833-41CE-A28B-A5E7F9F193AE";

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        RetailCustomer retailCustomer = espiPersistenceFactory.createRetailCustomer();
        authentication = new TestingAuthenticationToken(retailCustomer, null);
        espiPersistenceFactory.createAuthorization(retailCustomer);
    }

    @Test
    public void show_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/meterreadings/" + UUID + "/show").principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    public void show_displaysShowView() throws Exception {
        mockMvc.perform(get("/meterreadings/" + UUID + "/show").principal(authentication))
                .andExpect(view().name("/meterreadings/show"));
    }

    @Test
    public void show_setsMeterReadingModel() throws Exception {
        mockMvc.perform(get("/meterreadings/" + UUID + "/show").principal(authentication))
                .andExpect(model().attributeExists("meterReading"));
    }
}
