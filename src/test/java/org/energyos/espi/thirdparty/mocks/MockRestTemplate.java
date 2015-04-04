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

package org.energyos.espi.thirdparty.mocks;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MockRestTemplate extends RestTemplate {

	@SuppressWarnings("unchecked")
	public <T> T getForObject(String url, Class<T> responseType,
			Object... urlVariables) throws RestClientException {
		ClassPathResource sourceFile = new ClassPathResource(
				"/fixtures/test_usage_data.xml");
		String inputStreamString;
		try {
			inputStreamString = new Scanner(sourceFile.getInputStream(),
					"UTF-8").useDelimiter("\\A").next();
			return (T) inputStreamString;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RestClientException("The file import broke.");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method,
			HttpEntity<?> requestEntity, Class<T> responseType,
			Object... uriVariables) throws RestClientException {
		ClassPathResource sourceFile = new ClassPathResource(
				"/fixtures/test_usage_data.xml");
		String inputStreamString;
		try {
			inputStreamString = new Scanner(sourceFile.getInputStream(),
					"UTF-8").useDelimiter("\\A").next();
			return new ResponseEntity<>((T) inputStreamString, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RestClientException("The file import broke.");
		}
	}
}
