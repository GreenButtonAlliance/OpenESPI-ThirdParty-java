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

package org.greenbuttonalliance.espi.thirdparty.repository.impl;

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.service.AuthorizationService;
import org.greenbuttonalliance.espi.common.service.ImportService;
import org.greenbuttonalliance.espi.common.service.RetailCustomerService;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.greenbuttonalliance.espi.thirdparty.repository.UsagePointRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.List;

@Repository
public class UsagePointRESTRepositoryImpl implements UsagePointRESTRepository {
	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate template;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ImportService importService;

	@Autowired
	private UsagePointService usagePointService;

	@Autowired
	private UsagePointRepository usagePointRepository;

	@Autowired
	private RetailCustomerService retailCustomerService;

	// services initializers
	//
	public void setUsagePointService(UsagePointService usagePointService) {
		this.usagePointService = usagePointService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public void setRetailCustomerService(
			RetailCustomerService retailCustomerService) {
		this.retailCustomerService = retailCustomerService;
	}

	public void setUsagePointRepository(
			UsagePointRepository usagePointRepository) {
		this.usagePointRepository = usagePointRepository;
	}

	public void setTemplate(RestTemplate template) {
		this.template = template;
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public List<UsagePoint> findAllByRetailCustomerId(Long retailCustomerId)
			throws JAXBException {

		HttpEntity<String> httpResult = getUsagePoints(findAuthorization(retailCustomerId));
		ByteArrayInputStream bs = new ByteArrayInputStream(httpResult.getBody()
				.toString().getBytes());

		try {
			importService.importData(bs, retailCustomerId);
		} catch (Exception e) {
			// TODO need to pass the exception on through appropriately
		}

		List<UsagePoint> result;
		RetailCustomer retailCustomer = retailCustomerService
				.findById(retailCustomerId);
		result = usagePointService.findAllByRetailCustomer(retailCustomer);
		return result;
	}

	@Override
	public UsagePoint findByHashedId(Long retailCustomerId,
			String usagePointHashedId) throws JAXBException {
		List<UsagePoint> usagePoints = findAllByRetailCustomerId(retailCustomerId);

		for (UsagePoint usagePoint : usagePoints) {
			if (usagePoint.getHashedId().equalsIgnoreCase(usagePointHashedId)) {
				return usagePoint;
			}
		}

		return null;
	}

	private HttpEntity<String> getUsagePoints(Authorization authorization) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization",
				"Bearer " + authorization.getAccessToken());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

		return template.exchange(authorization.getResourceURI(),
				HttpMethod.GET, requestEntity, String.class);
	}

	private Authorization findAuthorization(Long retailCustomerId) {
		List<Authorization> authorizations = authorizationService
				.findAllByRetailCustomerId(retailCustomerId);
		return authorizations.get(authorizations.size() - 1);
	}

}
