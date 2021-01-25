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

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.Routes;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.service.AuthorizationService;
import org.greenbuttonalliance.espi.thirdparty.service.ResourceRESTService;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.mockito.Mockito.*;

public class UpdateServiceImplTests {

	public UpdateRESTServiceImpl updateService;
	public AuthorizationService authorizationService;
	public ResourceRESTService resourceRESTService;
	public Authorization authorization;
	public UsagePoint updateUsagePoint;
	public String uri;

	@Before
	public void before() {
		uri = Routes.buildDataCustodianRESTUsagePointGet("1", "1");
		updateUsagePoint = new UsagePoint();
		authorization = new Authorization();
		resourceRESTService = mock(ResourceRESTService.class);
		authorizationService = mock(AuthorizationService.class);

		when(authorizationService.findByURI(uri)).thenReturn(authorization);
		when(resourceRESTService.get(authorization, uri)).thenReturn(
				updateUsagePoint);

		updateService = new UpdateRESTServiceImpl();
		updateService.setResourceRESTService(resourceRESTService);
		updateService.setAuthorizationService(authorizationService);
	}

	@Test
	public void update_findsAuthorization() throws JAXBException {
		updateService.update(uri);

		verify(authorizationService).findByURI(uri);
	}

	@Test
	public void update_fetchesResource() throws JAXBException {
		updateService.update(uri);

		verify(resourceRESTService).get(authorization, uri);
	}

	@Test
	public void update_updatesResource() throws JAXBException {
		updateService.update(uri);

		verify(resourceRESTService).update(updateUsagePoint);
	}
}
