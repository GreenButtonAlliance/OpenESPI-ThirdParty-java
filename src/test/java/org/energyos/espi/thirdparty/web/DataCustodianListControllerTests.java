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

import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.service.DataCustodianService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataCustodianListControllerTests {

    protected DataCustodianListController controller;
    private DataCustodianService dataCustodianService;

    @Before
    public void before() {
        controller = new DataCustodianListController();
        dataCustodianService = mock(DataCustodianService.class);
        controller.setDataCustodianService(dataCustodianService);
    }

    @Test
    public void index_displaysIndexView() throws Exception {
        assertEquals("/RetailCustomer/DataCustodianList/index", controller.index(new ModelMap()));
    }

    @Test
    public void index_setsDataCustodianListModel() throws Exception {
        List<DataCustodian> dataCustodianList = new ArrayList<>();
        when(dataCustodianService.findAll()).thenReturn(dataCustodianList);
        ModelMap model = new ModelMap();

        controller.index(model);

        assertSame(dataCustodianList, model.get("dataCustodianList"));
    }
}
