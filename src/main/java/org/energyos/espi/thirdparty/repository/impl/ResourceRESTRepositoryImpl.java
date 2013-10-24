package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.IdentifiedObject;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;

@Repository
public class ResourceRESTRepositoryImpl implements ResourceRESTRepository {
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate template;

    @Autowired
    private Jaxb2Marshaller marshaller;

    public IdentifiedObject get(Authorization authorization, String url) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Bearer " + authorization.getAccessToken());
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

        HttpEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return (IdentifiedObject)marshaller.unmarshal(new StreamSource(response.getBody()));
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}

