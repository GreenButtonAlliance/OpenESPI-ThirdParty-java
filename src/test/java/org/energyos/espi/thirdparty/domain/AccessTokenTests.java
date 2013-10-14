package org.energyos.espi.thirdparty.domain;

import org.junit.Test;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessTokenTests {

    @Test
    public void json() throws IOException {
        HttpInputMessage message = mock(HttpInputMessage.class);
        when(message.getBody()).thenReturn(new ByteArrayInputStream(("{" +
                    "\"access_token\":\"6b945882-8349-471a-915f-25e791971248\"," +
                    "\"token_type\":\"bearer\"," +
                    "\"expires_in\":43199," +
                    "\"scope\":\"read write\"," +
                    "\"resource\":\"ResourceURI\"," +
                    "\"authorization\":\"AuthorizationURI\"" +
                "}").getBytes()));

        MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();

        converter.read(AccessToken.class, message);
    }
}
