package org.energyos.espi.thirdparty.mocks;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Scanner;

public class MockRestTemplate extends RestTemplate {

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        ClassPathResource sourceFile = new ClassPathResource("/fixtures/test_usage_data.xml");
        String inputStreamString;
        try {
            inputStreamString = new Scanner(sourceFile.getInputStream(),"UTF-8").useDelimiter("\\A").next();
            return (T)inputStreamString;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestClientException("The file import broke.");
        }

    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        ClassPathResource sourceFile = new ClassPathResource("/fixtures/test_usage_data.xml");
        String inputStreamString;
        try {
            inputStreamString = new Scanner(sourceFile.getInputStream(),"UTF-8").useDelimiter("\\A").next();
            return new ResponseEntity<>((T)inputStreamString, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestClientException("The file import broke.");
        }
    }
}
