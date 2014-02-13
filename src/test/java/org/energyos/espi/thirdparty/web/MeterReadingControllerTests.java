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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.service.impl.MeterReadingServiceImpl;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.ui.ModelMap;

public class MeterReadingControllerTests {

    private MeterReadingController controller;
    private MeterReadingServiceImpl service;

    @Before
    public void setupUp() {
        controller = new MeterReadingController();
        service = mock(MeterReadingServiceImpl.class);
        controller.setMeterReadingService(service);
    }

    @Test
    @Ignore
    public void show_displaysShowView() throws Exception {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(mock(RetailCustomer.class), null);
        assertEquals("/meterreadings/show", controller.show(1L, 1L, 1L, mock(ModelMap.class)));
    }

    @Test
    @Ignore
    public void show_setsMeterReadingModel() throws JAXBException {
        MeterReading meterReading = Factory.newMeterReading();
        ModelMap model = new ModelMap();
        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
        retailCustomer.setId(99L);
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(retailCustomer, null);
        when(service.findById(1L)).thenReturn(meterReading);

        controller.show(1L, 1L, 1L, model);

        assertEquals(meterReading, model.get("meterReading"));
    }
}
