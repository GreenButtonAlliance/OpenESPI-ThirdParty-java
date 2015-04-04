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

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.ResourceRESTService;
import org.energyos.espi.thirdparty.service.UpdateRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateRESTServiceImpl implements UpdateRESTService {
	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private ResourceRESTService resourceRESTService;

	public void update(String uri) throws JAXBException {
		Authorization authorization = authorizationService.findByURI(uri);
		UsagePoint resource = (UsagePoint) resourceRESTService.get(
				authorization, uri);
		resourceRESTService.update(resource);
	}

	public void setResourceRESTService(ResourceRESTService resourceRESTService) {
		this.resourceRESTService = resourceRESTService;
	}

	public void setAuthorizationService(
			AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public ResourceRESTService getResourceRESTService() {
		return this.resourceRESTService;
	}

	public AuthorizationService getAuthorizationService() {
		return this.authorizationService;
	}
}
