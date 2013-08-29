package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Repository
public class RetailCustomerRepositoryImpl implements RetailCustomerRepository{
    @Autowired
    @Qualifier("restTemplate")
    private RestOperations restClient;

    static final String URL = "http://datacustodian-dev.herokuapp.com/DataCustodian/API/feed";

    public void setRestClient(RestOperations restClient) {
        this.restClient = restClient;
    }

    @Override
    public String getUsagePoints() {
        return restClient.getForObject(URL, String.class);
    }
}
