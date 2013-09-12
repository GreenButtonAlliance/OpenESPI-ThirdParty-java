package org.energyos.espi.thirdparty.integration;

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class MeterReadingTests {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    private MeterReading meterReading;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        meterReading = new MeterReading();
        meterReading.setMRID("urn:uuid:E8B19EF0-6833-41CE-A28B-A5E7F9F193AE");
    }

    @Test
    public void show_returnsOkStatus() throws Exception {
        mockMvc.perform(get("/meterreadings/" + meterReading.getMRID() + "/show"))
                .andExpect(status().isOk());
    }

    @Test
    public void show_displaysShowView() throws Exception {
        mockMvc.perform(get("/meterreadings/" + meterReading.getMRID() + "/show"))
                .andExpect(view().name("/meterreadings/show"));
    }

    @Test
    public void show_setsMeterReadingModel() throws Exception {
        mockMvc.perform(get("/meterreadings/" + meterReading.getMRID() + "/show"))
                .andExpect(model().attributeExists("meterReading"));
    }
}
