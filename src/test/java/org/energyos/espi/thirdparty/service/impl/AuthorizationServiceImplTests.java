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

package org.energyos.espi.thirdparty.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.repositories.jpa.AuthorizationRepositoryImpl;
import org.energyos.espi.common.service.impl.AuthorizationServiceImpl;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Test;

public class AuthorizationServiceImplTests {

	private AuthorizationServiceImpl service;
	private AuthorizationRepositoryImpl repository;

	@Before
	public void before() {
		service = new AuthorizationServiceImpl();
		repository = mock(AuthorizationRepositoryImpl.class);
		service.setAuthorizationRepository(repository);
	}

	@Test
	public void findAllByRetailCustomer() {
		RetailCustomer retailCustomer = new RetailCustomer();

		service.findAllByRetailCustomerId(retailCustomer.getId());

		verify(repository).findAllByRetailCustomerId(retailCustomer.getId());
	}

	@Test
	public void findByState() {
		String state = "state";

		service.findByState(state);

		verify(repository).findByState(state);
	}

	@Test
	public void persist() {
		Authorization authorization = EspiFactory.newAuthorization(
				EspiFactory.newRetailCustomer(),
				EspiFactory.newApplicationInformation());

		service.persist(authorization);

		verify(repository).persist(authorization);
	}

	@Test
	public void merge() {
		Authorization authorization = EspiFactory.newAuthorization(
				EspiFactory.newRetailCustomer(),
				EspiFactory.newApplicationInformation());

		service.persist(authorization);

		authorization.setAccessToken(UUID.randomUUID().toString());

		service.merge(authorization);

		verify(repository).merge(authorization);
	}

	@Test
	public void findByUrl_findsUsagePointAuthorization() {
		String uri = "/espi/1_1/resource/RetailCustomer/1/UsagePoint/1";
		UsagePointRepository usagePointRepository = mock(UsagePointRepository.class);
		service.setUsagePointRepository(usagePointRepository);
		UsagePoint usagePoint = EspiFactory.newUsagePoint();
		Subscription subscription = EspiFactory.newSubscription(EspiFactory
				.newRetailCustomer());
		Authorization authorization = new Authorization();
		subscription.setAuthorization(authorization);
		usagePoint.setSubscription(subscription);
		when(usagePointRepository.findByURI(uri)).thenReturn(usagePoint);

		assertEquals(authorization, service.findByURI(uri));
	}

}
