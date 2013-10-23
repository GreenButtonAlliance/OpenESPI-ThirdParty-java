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

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.service.MeterReadingService;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import javax.xml.bind.JAXBException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MeterReadingControllerTests {

    private MeterReadingController controller;
    private MeterReadingService service;

    @Before
    public void setupUp() {
        controller = new MeterReadingController();
        service = mock(MeterReadingService.class);
        controller.setMeterReadingService(service);
    }

    @Test
    public void show_displaysShowView() throws Exception {
        assertEquals("/meterreadings/show", controller.show(UUID.randomUUID().toString(), mock(ModelMap.class)));
    }

    @Test
    public void show_setsMeterReadingModel() throws JAXBException {
        MeterReading meterReading = Factory.newMeterReading();
        ModelMap model = new ModelMap();
        when(service.findByUUID(any(UUID.class))).thenReturn(meterReading);

        controller.show(UUID.randomUUID().toString(), model);

        assertEquals(meterReading, model.get("meterReading"));
    }
}
