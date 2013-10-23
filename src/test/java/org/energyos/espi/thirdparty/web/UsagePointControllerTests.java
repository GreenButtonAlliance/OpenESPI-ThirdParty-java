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
import org.energyos.espi.thirdparty.service.impl.UsagePointServiceImpl;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UsagePointControllerTests {

    private UsagePointController controller;
    private UsagePointService service;

    @Before
    public void setup() {
        controller = new UsagePointController();
        service = mock(UsagePointServiceImpl.class);
        controller.setUsagePointService(service);
    }

    @Test
    public void index_displaysIndexView() throws Exception {
        assertEquals("/usagepoints/index", controller.index(mock(ModelMap.class), mock(Authentication.class)));
    }

    @Test
    public void index_findsUsagePointsForLoggedInCustomer() throws JAXBException {

        RetailCustomer customer = new RetailCustomer();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customer);

        controller.index(mock(ModelMap.class), authentication);
        verify(service).findAllByRetailCustomer(customer);
    }

    @Test
    public void show_displaysShowView() throws Exception {
        assertEquals("/usagepoints/show", controller.show("7BC41774-7190-4864-841C-861AC76D46C2", mock(ModelMap.class)));
    }

    @Test
    public void show_findsTheUsagePointByUUID() throws Exception {
        UsagePoint usagePoint = Factory.newUsagePoint();
        String hashedId = "hashedId";
        when(service.findByHashedId(hashedId)).thenReturn(usagePoint);

        controller.show(hashedId, mock(ModelMap.class));
        verify(service).findByHashedId(hashedId);
    }
}
