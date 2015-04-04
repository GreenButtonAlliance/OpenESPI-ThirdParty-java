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

package org.energyos.espi.thirdparty.web;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.service.BatchListService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class NotificationControllerTests {

	@Mock
	private BatchListService batchListService;

	public NotificationController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new NotificationController();
		controller.setBatchListService(batchListService);
		controller.setMarshaller(mock(Jaxb2Marshaller.class));
	}

	@Test
	@Ignore
	public void notification() throws IOException {
		controller.notification(mock(HttpServletResponse.class),
				mock(InputStream.class));
		verify(batchListService).persist(any(BatchList.class));
	}
}
