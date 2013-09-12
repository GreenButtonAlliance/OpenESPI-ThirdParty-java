package org.energyos.espi.thirdparty.mocks;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Scanner;

public class MockRestTemplate extends RestTemplate {

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        ClassPathResource sourceFile = new ClassPathResource("/fixtures/15minLP_15Days.xml");
        String inputStreamString = null;
        try {
            inputStreamString = new Scanner(sourceFile.getInputStream(),"UTF-8").useDelimiter("\\A").next();
            return (T)inputStreamString;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestClientException("The file import broke.");
        }

    }
}
