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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.StateService;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.thirdparty.BaseTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;

public class ScopeSelectionControllerTests extends BaseTest {

	private ScopeSelectionController controller;
	@Mock
	private ApplicationInformationService applicationInformationService;
	@Mock
	private AuthorizationService authorizationService;
	@Mock
	private StateService stateService;

	@Before
	public void before() {
		controller = new ScopeSelectionController();
		controller
				.setApplicationInformationService(applicationInformationService);
		controller.setAuthorizationService(authorizationService);
		controller.setStateService(stateService);
	}

	@Test
	public void post_scopeSelection_redirects() throws Exception {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		when(
				applicationInformationService
						.findByDataCustodianClientId(eq(applicationInformation
								.getDataCustodianId()))).thenReturn(
				applicationInformation);

		String redirectURL = controller.scopeSelection(
				applicationInformation.getDataCustodianId(),
				applicationInformation.getThirdPartyScopeSelectionScreenURI());

		assertEquals(String.format(
				"redirect:%s?scope=%s&scope=%s&ThirdPartyID=%s",
				applicationInformation.getThirdPartyScopeSelectionScreenURI(),
				applicationInformation.getScopeArray()[0],
				applicationInformation.getScopeArray()[1],
				applicationInformation.getClientId()), redirectURL);
	}

	@Test
	public void get_scopeSelection_displaysScopeSelectionView()
			throws Exception {
		ScopeSelectionController controller = new ScopeSelectionController();

		assertEquals("/RetailCustomer/ScopeSelection",
				controller.scopeSelection(new String[] {}, new ModelMap()));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void get_scopeSelection_setsScopeListModel() throws Exception {
		ModelMap model = new ModelMap();

		controller.scopeSelection(new String[] { "scope1", "scope2" }, model);

		assertTrue(((List<String>) model.get("scopeList")).size() > 0);
	}

	@Test
	@Ignore
	public void post_scopeAuthorization_redirects() throws Exception {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		when(
				applicationInformationService
						.findByDataCustodianClientId(eq(applicationInformation
								.getDataCustodianId()))).thenReturn(
				applicationInformation);

		String expectedRedirectURL = String
				.format("redirect:%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s&state=",
						applicationInformation
								.getAuthorizationServerAuthorizationEndpoint(),
						applicationInformation.getClientId(),
						applicationInformation.getRedirectUri(), "code",
						applicationInformation.getScopeArray()[0]);

		Authentication principal = mock(Authentication.class);
		when(principal.getPrincipal()).thenReturn(
				EspiFactory.newRetailCustomer());

		assertTrue(controller.scopeAuthorization(
				applicationInformation.getScopeArray()[0],
				applicationInformation.getDataCustodianId(), principal)
				.startsWith(expectedRedirectURL));
	}

	@Test
	@Ignore
	public void post_scopeAuthorization_createsAuthorization() throws Exception {
		ApplicationInformation applicationInformation = EspiFactory
				.newApplicationInformation();
		when(
				applicationInformationService
						.findByDataCustodianClientId(eq(applicationInformation
								.getDataCustodianId()))).thenReturn(
				applicationInformation);

		AuthorizationService authorizationService = mock(AuthorizationService.class);
		controller.setAuthorizationService(authorizationService);

		Authentication principal = mock(Authentication.class);
		when(principal.getPrincipal()).thenReturn(
				EspiFactory.newRetailCustomer());

		controller.scopeAuthorization(
				applicationInformation.getScopeArray()[0],
				applicationInformation.getDataCustodianId(), principal);

		verify(authorizationService).persist(any(Authorization.class));
	}
}
