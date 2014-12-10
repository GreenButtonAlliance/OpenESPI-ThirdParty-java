package org.energyos.espi.thirdparty.mocks;

import org.energyos.espi.common.domain.AccessToken;
import org.energyos.espi.thirdparty.web.ClientRestTemplate;
import org.springframework.web.client.RestClientException;

public class MockClientRestTemplate extends ClientRestTemplate {

    @SuppressWarnings("unchecked")
	public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        AccessToken accessToken = new AccessToken();

        accessToken.setAccessToken("6b945882-8349-471a-915f-25e791971248");
        accessToken.setTokenType("Bearer");
        accessToken.setExpiresIn(43199L);
        accessToken.setScope("read write");

        return (T)accessToken;
    }
}
