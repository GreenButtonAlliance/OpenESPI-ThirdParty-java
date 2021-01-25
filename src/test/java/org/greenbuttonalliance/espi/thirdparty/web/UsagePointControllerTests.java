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

package org.greenbuttonalliance.espi.thirdparty.web;

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.greenbuttonalliance.espi.common.service.impl.ResourceServiceImpl;
import org.greenbuttonalliance.espi.common.service.impl.UsagePointServiceImpl;
import org.greenbuttonalliance.espi.common.test.EspiFactory;
import org.greenbuttonalliance.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class UsagePointControllerTests {

	private UsagePointController controller;
	private UsagePointService service;
	private ResourceServiceImpl resourceService;
	private Authentication authentication;
	private RetailCustomer retailCustomer;

	@Before
	public void setup() {
		controller = new UsagePointController();
		service = mock(UsagePointServiceImpl.class);
		resourceService = mock(ResourceServiceImpl.class);
		controller.setResourceService(resourceService);
		authentication = mock(Authentication.class);
		retailCustomer = EspiFactory.newRetailCustomer();
		when(authentication.getPrincipal()).thenReturn(retailCustomer);
	}

	@Test
	@Ignore
	public void index_displaysIndexView() throws Exception {
		when(resourceService.findAllIds(UsagePoint.class)).thenReturn(
				new ArrayList<Long>());
		assertEquals("/usagepoints/index",
				controller.index(mock(ModelMap.class), authentication));
	}

	@Test
	@Ignore
	public void index_findsUsagePointsForLoggedInCustomer()
			throws JAXBException {
		controller.index(mock(ModelMap.class), authentication);
		verify(resourceService).findAllIdsByXPath(1L, UsagePoint.class).equals(
				null);

	}

	@Test
	@Ignore
	public void show_displaysShowView() throws Exception {
		when(resourceService.findById(anyLong(), UsagePoint.class)).thenReturn(
				EspiFactory.newUsagePoint());
		assertEquals("/usagepoints/show",
				controller.show(1L, 1L, mock(ModelMap.class)));
	}

	@Test
	@Ignore
	public void show_findsTheUsagePointByUUID() throws Exception {
		UsagePoint usagePoint = Factory.newUsagePoint();
		String hashedId = "hashedId";
		when(service.findByHashedId(hashedId)).thenReturn(usagePoint);

		controller.show(1L, 1L, mock(ModelMap.class));
		verify(resourceService).findById(retailCustomer.getId(),
				UsagePoint.class);
	}
}
