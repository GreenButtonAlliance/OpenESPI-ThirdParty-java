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

import javax.xml.transform.stream.StreamSource;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ResourceRESTRepositoryImpl implements ResourceRESTRepository {
	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate template;

	@Autowired
	@Qualifier(value = "atomMarshaller")
	private Jaxb2Marshaller marshaller;

	public IdentifiedObject get(Authorization authorization, String url) {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Authorization",
				"Bearer " + authorization.getAccessToken());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

		HttpEntity<String> response = template.exchange(url, HttpMethod.GET,
				requestEntity, String.class);

		return (IdentifiedObject) marshaller.unmarshal(new StreamSource(
				response.getBody()));
	}

	public void setRestTemplate(RestTemplate template) {
		this.template = template;
	}

	public RestTemplate getRestTemplate() {
		return this.template;
	}

	public void setJaxb2Marshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Jaxb2Marshaller getJaxb2Marshaller() {
		return this.marshaller;
	}

}
