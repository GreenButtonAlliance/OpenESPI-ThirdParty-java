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

package org.energyos.espi.thirdparty.web.custodian;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.thirdparty.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTODIAN')")
public class AdministratorController extends BaseController {

	@Autowired
	private RetailCustomerService service;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ImportService importService;

	@RequestMapping(value = Routes.ROOT_SERVICE_STATUS, method = RequestMethod.GET)
	public String showServiceStatus(ModelMap model) {

		ApplicationInformation applicationInformation = resourceService
				.findById(1L, ApplicationInformation.class);
		String statusUri = applicationInformation
				.getAuthorizationServerAuthorizationEndpoint()
				+ "/ReadServiceStatus";
		// not sure this will work w/o the right seed information
		//
		Authorization authorization = resourceService.findByResourceUri(
				statusUri, Authorization.class);
		RetailCustomer retailCustomer = authorization.getRetailCustomer();

		String accessToken = authorization.getAccessToken();
		String serviceStatus = "OK";

		try {

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.set("Authorization", "Bearer " + accessToken);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

			// get the subscription
			HttpEntity<String> httpResult = restTemplate.exchange(statusUri,
					HttpMethod.GET, requestEntity, String.class);

			// import it into the repository
			ByteArrayInputStream bs = new ByteArrayInputStream(httpResult
					.getBody().toString().getBytes());

			importService.importData(bs, retailCustomer.getId());

			List<EntryType> entries = importService.getEntries();

			// TODO: Use-Case 1 registration - service status

		} catch (Exception e) {
			// nothing there, so log the fact and move on. It will
			// get imported later.
			e.printStackTrace();
		}
		model.put("serviceStatus", serviceStatus);

		return "/custodian/datacustodian/showservicestatus";
	}

	public void setRetailCustomerService(RetailCustomerService service) {
		this.service = service;
	}

	public RetailCustomerService getRetailCustomerService() {
		return this.service;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return this.resourceService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public ImportService getImportService() {
		return this.importService;
	}

}
