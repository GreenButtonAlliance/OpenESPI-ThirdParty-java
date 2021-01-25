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
import org.greenbuttonalliance.espi.common.domain.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.ResourceRepository;
import org.greenbuttonalliance.espi.thirdparty.repository.ResourceRESTRepository;
import org.greenbuttonalliance.espi.thirdparty.service.ResourceRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRESTServiceImpl implements ResourceRESTService {
	@Autowired
	private ResourceRESTRepository resourceRESTRepository;

	@Autowired
	private ResourceRepository resourceRepository;

	public IdentifiedObject get(Authorization authorization, String uri) {
		return resourceRESTRepository.get(authorization, uri);
	}

	@Override
	public void update(UsagePoint resource) {
		resourceRepository.update(resource);
	}

	public void setResourceRESTRepository(
			ResourceRESTRepository resourceRESTRepository) {
		this.resourceRESTRepository = resourceRESTRepository;
	}

	public void setResourceRepository(ResourceRepository resourceRepository) {
		this.resourceRepository = resourceRepository;
	}

	public ResourceRESTRepository getResourceRESTRepository() {
		return this.resourceRESTRepository;
	}

	public ResourceRepository getResourceRepository() {
		return this.resourceRepository;
	}
}
