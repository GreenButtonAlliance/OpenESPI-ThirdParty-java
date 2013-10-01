package org.energyos.espi.thirdparty.web;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ClientRestTemplate extends RestTemplate {

    public ClientRestTemplate() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("tonr", "secret"));
        httpClient.setCredentialsProvider(credentialsProvider);
        ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);

        this.setRequestFactory(rf);
    }
}
