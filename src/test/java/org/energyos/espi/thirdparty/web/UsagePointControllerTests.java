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

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.service.impl.UsagePointServiceImpl;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UsagePointControllerTests {

    private UsagePointController controller;
    private UsagePointService service;
    private UsagePointRESTRepository usagePointRESTRepository;
    private Authentication authentication;
    private RetailCustomer retailCustomer;

    @Before
    public void setup() {
        controller = new UsagePointController();
        service = mock(UsagePointServiceImpl.class);
        usagePointRESTRepository = mock(UsagePointRESTRepository.class);
        controller.setUsagePointRESTRepository(usagePointRESTRepository);
        authentication = mock(Authentication.class);
        retailCustomer = EspiFactory.newRetailCustomer();
        when(authentication.getPrincipal()).thenReturn(retailCustomer);
    }

    @Test
    public void index_displaysIndexView() throws Exception {
        when(usagePointRESTRepository.findAllByRetailCustomerId(anyLong())).thenReturn(new ArrayList<UsagePoint>());
        assertEquals("/usagepoints/index", controller.index(mock(ModelMap.class), authentication));
    }

    @Test
    public void index_findsUsagePointsForLoggedInCustomer() throws JAXBException {
        controller.index(mock(ModelMap.class), authentication);
        verify(usagePointRESTRepository).findAllByRetailCustomerId(retailCustomer.getId());
    }

    @Test
    public void show_displaysShowView() throws Exception {
        when(usagePointRESTRepository.findByHashedId(anyLong(), anyString())).thenReturn(EspiFactory.newUsagePoint());
        assertEquals("/usagepoints/show", controller.show("48C2A019-5598-4E16-B0F9-49E4FF27F5FB", mock(ModelMap.class), authentication));
    }

    @Test
    public void show_findsTheUsagePointByUUID() throws Exception {
        UsagePoint usagePoint = Factory.newUsagePoint();
        String hashedId = "hashedId";
        when(service.findByHashedId(hashedId)).thenReturn(usagePoint);

        controller.show(hashedId, mock(ModelMap.class), authentication);
        verify(usagePointRESTRepository).findByHashedId(retailCustomer.getId(), hashedId);
    }
}
