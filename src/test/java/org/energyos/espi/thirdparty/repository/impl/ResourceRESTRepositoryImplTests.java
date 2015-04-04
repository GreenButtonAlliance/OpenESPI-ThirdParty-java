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

package org.energyos.espi.thirdparty.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.Routes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

public class ResourceRESTRepositoryImplTests {

	public ResourceRESTRepositoryImpl repository;
	public Jaxb2Marshaller marshaller;
	public RestTemplate template;
	public Authorization authorization;
	public String uri;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		repository = new ResourceRESTRepositoryImpl();
		marshaller = mock(Jaxb2Marshaller.class);

		template = mock(RestTemplate.class);
		ResponseEntity<String> response = new ResponseEntity<String>(
				HttpStatus.OK);
		when(
				template.exchange(anyString(), eq(HttpMethod.GET),
						any(HttpEntity.class), any(Class.class))).thenReturn(
				response);

		repository.setRestTemplate(template);
		repository.setJaxb2Marshaller(marshaller);

		authorization = new Authorization();
		authorization.setAccessToken("token");
		uri = Routes.DATA_CUSTODIAN_REST_USAGE_POINT_GET;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void get_fetchesResource() throws JAXBException {
		repository.get(authorization, uri);

		verify(template).exchange(anyString(), eq(HttpMethod.GET),
				any(HttpEntity.class), any(Class.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void get_usesAccessToken() throws JAXBException {
		repository.get(authorization, uri);

		@SuppressWarnings("rawtypes")
		ArgumentCaptor<HttpEntity> argumentCaptor = ArgumentCaptor
				.forClass(HttpEntity.class);
		verify(template).exchange(anyString(), eq(HttpMethod.GET),
				argumentCaptor.capture(), any(Class.class));
		assertEquals("Bearer token", argumentCaptor.getValue().getHeaders()
				.get("Authorization").get(0));
	}

	@Test
	public void get_unmarshallsResource() throws JAXBException {
		repository.get(authorization, uri);

		verify(marshaller).unmarshal(any(Source.class));
	}
}
