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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.junit.Test;

public class ResourceServiceImplTests {

	@Test
	public void get_fetchesResource() throws JAXBException {
		ResourceRESTRepository repository = mock(ResourceRESTRepository.class);

		ResourceRESTServiceImpl service = new ResourceRESTServiceImpl();
		service.setResourceRESTRepository(repository);

		Authorization authorization = new Authorization();
		String url = Routes.DATA_CUSTODIAN_REST_USAGE_POINT_GET;

		service.get(authorization, url);

		verify(repository).get(authorization, url);
	}

	@Test
	public void update_updatesResource() throws JAXBException {
		ResourceRepository repository = mock(ResourceRepository.class);

		ResourceRESTServiceImpl service = new ResourceRESTServiceImpl();
		service.setResourceRepository(repository);

		UsagePoint resource = new UsagePoint();

		service.update(resource);

		verify(repository).update(resource);
	}
}
