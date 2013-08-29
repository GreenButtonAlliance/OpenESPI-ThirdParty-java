package org.energyos.espi.thirdparty.service.impl;


import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.repository.impl.RetailCustomerRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class RetailCustomerRepositoryImplTests {

    private RetailCustomerRepositoryImpl repo;
    private RestOperations restClient;

    @Before
    public void setup() {
        repo = new RetailCustomerRepositoryImpl();
        restClient = mock(RestTemplate.class);
        repo.setRestClient(restClient);
    }

    @Test
    public void getUsagePoints() {
        String url = "http://datacustodian-dev.herokuapp.com/DataCustodian/API/feed";
        String atomFeed = "U CAN HAZ USAGEPOINTS";
        when(restClient.getForObject(url, String.class)).thenReturn(atomFeed);

        String result = repo.getUsagePoints();

        verify(restClient).getForObject(url, String.class);
        assertEquals(atomFeed, result);
    }
}
