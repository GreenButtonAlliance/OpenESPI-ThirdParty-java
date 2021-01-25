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

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.impl.UsagePointServiceImpl;
import org.greenbuttonalliance.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsagePointServiceImplTests {

	private UsagePointRepository repository;
	private UsagePointServiceImpl service;

	@Before
	public void before() {
		service = new UsagePointServiceImpl();
		repository = mock(UsagePointRepository.class);
		service.setRepository(repository);
	}

	@Test
	public void findAllByRetailCustomer_returnsUsagePointList()
			throws JAXBException {
		List<UsagePoint> usagePointList = new ArrayList<>();
		when(repository.findAllByRetailCustomerId(any(Long.class))).thenReturn(
				usagePointList);
		RetailCustomer retailCustomer = new RetailCustomer();
		retailCustomer.setId(1L);

		assertEquals(usagePointList,
				service.findAllByRetailCustomer(retailCustomer));
	}

	@Test
	public void findById_returnsUsagePoint() throws JAXBException {
		UsagePoint usagePoint = Factory.newUsagePoint();

		when(repository.findById(any(Long.class))).thenReturn(usagePoint);

		assertEquals(usagePoint, service.findById(1L));
	}

	@Test
	public void findByUUID_returnsUsagePoint() throws JAXBException {
		UsagePoint usagePoint = Factory.newUsagePoint();

		when(repository.findByUUID(any(UUID.class))).thenReturn(usagePoint);

		assertEquals(usagePoint, service.findByUUID(usagePoint.getUUID()));
	}
}
