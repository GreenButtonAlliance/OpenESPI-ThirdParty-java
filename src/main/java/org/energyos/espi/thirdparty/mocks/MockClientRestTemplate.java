package org.energyos.espi.thirdparty.mocks;

import org.energyos.espi.thirdparty.domain.AccessToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Scanner;

public class MockClientRestTemplate extends RestTemplate {

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        AccessToken accessToken = new AccessToken();

        accessToken.setAccessToken("6b945882-8349-471a-915f-25e791971248");
        accessToken.setTokenType("bearer");
        accessToken.setExpiresIn(43199L);
        accessToken.setScope("read write");

        return (T)accessToken;
    }
}
