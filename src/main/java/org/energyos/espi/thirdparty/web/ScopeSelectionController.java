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

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ScopeSelectionController extends BaseController {

	@Autowired
	private ApplicationInformationService applicationInformationService;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	@Qualifier("stateService")
	private StateService stateService;

	@RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.GET)
	public String scopeSelection(@RequestParam("scope") String[] scopes,
			ModelMap model) throws JAXBException {

		System.out
				.printf("ScopeSelectionController: HttpRequest Method: GET, scopes: %s\n",
						Arrays.asList(scopes));
		
		model.put("scopeList", Arrays.asList(scopes));

		return "/RetailCustomer/ScopeSelection";
	}

	@RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN_WITH_RETAIL_CUSTOMER_ID, method = RequestMethod.POST)
	public String scopeSelection(
			@RequestParam("Data_custodian") String dataCustodianId,
			@RequestParam("Data_custodian_URL") String dataCustodianURL)
			throws JAXBException {

		System.out
				.printf("ScopeSelectionController: HttpRequest Method: POST, dataCustodianID: %s dataCustodianURL: %s\n",
						dataCustodianId, dataCustodianURL);
		
		ApplicationInformation applicationInformation = applicationInformationService
				.findByDataCustodianClientId(dataCustodianId);
		return "redirect:" + dataCustodianURL + "?"
				+ newScopeParams(applicationInformation.getScope())
				+ "&ThirdPartyID=" + applicationInformation.getClientId();
	}

	@RequestMapping(value = Routes.THIRD_PARTY_SCOPE_SELECTION_SCREEN, method = RequestMethod.POST)
	public String scopeAuthorization(@RequestParam("scope") String scope,
			@RequestParam("DataCustodianID") String dataCustodianId,
			Principal principal) throws JAXBException {

		System.out
				.printf("ScopeSelectionController: HttpRequest Method: POST, scope: %s dataCustodianID: %s\n",
						scope, dataCustodianId);
		
		ApplicationInformation applicationInformation = applicationInformationService
				.findByDataCustodianClientId(dataCustodianId);

		try {
			// Does an ACTIVE authorization record exist for the requested Scope
			Authorization currentAuthorization = authorizationService
					.findByScope(scope, currentCustomer(principal).getId());

			// Is this a valid authorization record?
			if (currentAuthorization.getStatus() == null) {

				// Delete the invalid record and continue request
				authorizationService.delete(currentAuthorization);
				throw new NoResultException();
			}

			else {

				// Is the existing authorization record Active
				if (!currentAuthorization.getStatus().equals("1")) {

					// No, create a new authorization record entry
					throw new NoResultException();
				}
			}

		} catch (NoResultException | EmptyResultDataAccessException e) {

			// No authorization record exist for the requested Scope
			Authorization authorization = new Authorization();

			// Initialize authorization record content
			authorization.setApplicationInformation(applicationInformation);
			authorization.setThirdParty(applicationInformation.getClientId());
			authorization.setRetailCustomer(currentCustomer(principal));
			authorization.setState(stateService.newState());
			authorization.setUUID(UUID.randomUUID());
			authorization.setResponseType("code");
			authorization.setScope(scope);
			authorizationService.persist(authorization);

			return "redirect:"
					+ applicationInformation
							.getAuthorizationServerAuthorizationEndpoint()
					+ "?client_id=" + applicationInformation.getClientId()
					+ "&redirect_uri="
					+ applicationInformation.getRedirectUri()
					+ "&response_type=code&scope=" + scope + "&state="
					+ authorization.getState();
		}

		// TODO: If an Oauth access token already exists, do we want to display
		// the "UsagePoint" screen?
		// Display the Authorization List screen if an OAuth access token
		// already exist
		return "redirect:/RetailCustomer/" + currentCustomer(principal).getId()
				+ "/AuthorizationList";

	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setStateService(StateService stateService) {
		this.stateService = stateService;
	}

	public void setApplicationInformationService(
			ApplicationInformationService applicationInformationService) {
		this.applicationInformationService = applicationInformationService;
	}

	public static String newScopeParams(Set<String> scopes) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String scope : scopes) {
			if (i > 0)
				sb.append("&");
			sb.append("scope=" + scope);
			i++;
		}
		return sb.toString();
	}
}
