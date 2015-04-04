/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
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

package org.energyos.espi.thirdparty.web.tools;

import static org.energyos.espi.common.test.EspiFactory.newBatchList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.service.BatchListService;
import org.energyos.espi.thirdparty.BaseTest;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ui.ModelMap;

public class BatchListControllerTest extends BaseTest {
	BatchListController controller;

	@Mock
	private BatchListService batchListService;

	@Mock
	Jaxb2Marshaller marshaller;

	@Test
	public void index() throws IOException {
		controller = new BatchListController();
		List<BatchList> batchListList = new ArrayList<>();
		batchListList.add(newBatchList());

		when(batchListService.findAll()).thenReturn(batchListList);
		controller.setBatchListService(batchListService);

		controller.index(mock(ModelMap.class));

		verify(batchListService).findAll();
	}
}
