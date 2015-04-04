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
import java.util.GregorianCalendar;

import javax.persistence.NoResultException;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.AccessToken;
import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class AuthorizationController extends BaseController {

	@Autowired
	private AuthorizationService authorizationService;

	// TODO The following is legacy code that will do the import of a
	// subscription
	// from a DC. Needs to be separated out of the "repository" level and be
	// callable as a TP specific service level.

	@Autowired
	private UsagePointRESTRepository usagePointRESTRepository;

	@Autowired
	@Qualifier("clientRestTemplateFactory")
	private ClientRestTemplateFactory templateFactory;

	@RequestMapping(value = Routes.THIRD_PARTY_OAUTH_CODE_CALLBACK, method = RequestMethod.GET)
	public String authorization(
			String code,
			String state,
			ModelMap model,
			Principal principal,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "error_description", required = false) String error_description,
			@RequestParam(value = "error_uri", required = false) String error_uri) {

		try {

			// Is /oauth/authorization response valid (i.e. is the "state"
			// element correct)?
			Authorization authorization = authorizationService
					.findByState(state);

			// Process valid /oauth/authorization response
			ApplicationInformation applicationInformation = authorization
					.getApplicationInformation();

			// Verify /oauth/authorization Endpoint process completed
			// successfully
			if (code != null) {
				try {

					// Update Authorization record with returned authorization
					// code for audit purposes
					authorization.setCode(code);
					authorization.setGrantType("authorization_code");
					authorization.setUpdated(new GregorianCalendar());
					authorizationService.merge(authorization);

					// Format /oauth/token Endpoint request
					String url = String
							.format("%s?redirect_uri=%s&code=%s&grant_type=authorization_code",
									applicationInformation
											.getAuthorizationServerTokenEndpoint(),
									applicationInformation.getRedirectUri(),
									code);

					// Build /oauth/token Endpoint request
					ClientRestTemplate restTemplate = templateFactory
							.newClientRestTemplate(
									applicationInformation.getClientId(),
									applicationInformation.getClientSecret());

					// Issue /oauth/token Endpoint request
					AccessToken token = restTemplate.getForObject(url,
							AccessToken.class);

					// Process /oauth/token Endpoint response

					if (token.getAccessToken() != null) {
						authorization.setAccessToken(token.getAccessToken());
						authorization.setTokenType(token.getTokenType());
						authorization.setExpiresIn(token.getExpiresIn());
						authorization.setRefreshToken(token.getRefreshToken());
						authorization.setScope(token.getScope());
						authorization.setAuthorizationURI(token
								.getAuthorizationURI());
						authorization.setResourceURI(token.getResourceURI());
						authorization.setUpdated(new GregorianCalendar());
						authorization.setStatus("1"); // Set authorization
														// record status as
														// "Active"
						authorization.setState(null); // Clear State as a
														// security measure

						// Update authorization record with /oauth/token
						// response data
						authorizationService.merge(authorization);

						// now do the initial import of the Authorized Resource,
						// if it is
						// not ready, then we will wait till we receive a Notify
						// or the UX call for it.
						// TODO: create a Subscription to work with if needed

						RetailCustomer currentCustomer = currentCustomer(principal);

						try {
							usagePointRESTRepository
									.findAllByRetailCustomerId(currentCustomer
											.getId());

						} catch (JAXBException e) {
							// nothing there, so log the fact and move on. It
							// will get imported later.
							System.out.printf(
									"\nThirdParty Import Exception: %s\n",
									e.toString());
							e.printStackTrace();
						}
					} else {

						System.out
								.printf("\n/oauth/token Request did not return an access token\n");
					}

				} catch (HttpClientErrorException x) {

					// TODO: Extract error, error_description and error_uri from
					// JSON response. Currently recording null for all three
					// fields.

					// Update authorization record
					System.out.printf("\nHTTPClientException: %s\n",
							x.toString());

					authorization.setError(error);
					authorization.setErrorDescription(error_description);
					authorization.setErrorUri(error_uri);
					authorization.setUpdated(new GregorianCalendar());
					authorization.setStatus("2"); // Set authorization record
													// status as "Denied"
					authorization.setState(null); // Clear State as a security
													// measure
					authorizationService.merge(authorization);

					// TODO: Should the "message" differ based on the exception?
					throw new UserDeniedAuthorizationException(
							"Unable to retrieve OAuth token", x);
				}
			} else {

				System.out
						.printf("\nOAuth2 authorization_request returned an error:\n");
				System.out.printf("Error:             " + error + "\n");
				System.out.printf("Error_description: " + error_description
						+ "\n");
				System.out.printf("Error_uri:         " + error_uri + "\n");

				// Update authorization record with error response
				authorization.setError(error);
				authorization.setErrorDescription(error_description);
				authorization.setErrorUri(error_uri);
				authorization.setUpdated(new GregorianCalendar());
				authorization.setStatus("2"); // Set authorization record status
												// as "Denied"
				authorization.setState(null); // Clear State as a security
												// measure
				authorizationService.merge(authorization);

				throw new UserDeniedAuthorizationException("Error: "
						+ error_description);

			}

		} catch (NoResultException | EmptyResultDataAccessException e) {

			// We received an invalid /oauth/authorization response
			// TODO: Log receipt of an invalid /oauth/authorization response
			return "/home";

		}

		return "redirect:/RetailCustomer/" + currentCustomer(principal).getId()
				+ "/AuthorizationList";
	}

	@RequestMapping(value = Routes.THIRD_PARTY_AUTHORIZATION, method = RequestMethod.GET)
	public String index(ModelMap model, Authentication principal) {
		model.put(
				"authorizationList",
				authorizationService.findAllByRetailCustomerId(currentCustomer(
						principal).getId()));
		return "/RetailCustomer/AuthorizationList/index";
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public AuthorizationService getAuthorizationService() {
		return this.authorizationService;
	}

	public void setUsagePointRESTRepository(
			UsagePointRESTRepository usagePointRESTRepository) {
		this.usagePointRESTRepository = usagePointRESTRepository;
	}

	public UsagePointRESTRepository getUsagePointRESTRepository() {
		return this.usagePointRESTRepository;
	}

	public void setClientRestTemplateFactory(
			ClientRestTemplateFactory templateFactory) {
		this.templateFactory = templateFactory;
	}

	public ClientRestTemplateFactory getClientRestTemplateFactory() {
		return this.templateFactory;
	}

}
