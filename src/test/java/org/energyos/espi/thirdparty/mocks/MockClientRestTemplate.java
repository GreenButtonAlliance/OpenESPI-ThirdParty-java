package org.energyos.espi.thirdparty.mocks;

import org.energyos.espi.common.domain.AccessToken;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
