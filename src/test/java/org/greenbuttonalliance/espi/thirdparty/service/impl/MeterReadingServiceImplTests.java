/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.thirdparty.service.impl;

import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.thirdparty.repository.MeterReadingRESTRepository;
import org.greenbuttonalliance.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterReadingServiceImplTests {

	private MeterReadingRESTRepository repository;
	private MeterReadingRESTServiceImpl service;

	@Before
	public void before() {
		service = new MeterReadingRESTServiceImpl();
		repository = mock(MeterReadingRESTRepository.class);
		service.setRepository(repository);
	}

	@Test
	public void findByUUID_returnsMeterReading() throws JAXBException {
		MeterReading meterReading = Factory.newMeterReading();

		when(repository.findByUUID(eq(1L), any(UUID.class))).thenReturn(
				meterReading);

		assertEquals(meterReading, service.findByUUID(1L, UUID.randomUUID()));
	}
}
