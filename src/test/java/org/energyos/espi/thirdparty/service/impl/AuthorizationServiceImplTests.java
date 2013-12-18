package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.AuthorizationRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.repositories.jpa.AuthorizationRepositoryImpl;
import org.energyos.espi.common.service.impl.AuthorizationServiceImpl;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorizationServiceImplTests {


    private AuthorizationServiceImpl service;
    private AuthorizationRepositoryImpl repository;

    @Before
    public void before() {
        service = new AuthorizationServiceImpl();
        repository = mock(AuthorizationRepositoryImpl.class);
        service.setAuthorizationRepository(repository);
    }

    @Test
    public void findAllByRetailCustomer() {
        RetailCustomer retailCustomer = new RetailCustomer();

        service.findAllByRetailCustomerId(retailCustomer.getId());

        verify(repository).findAllByRetailCustomerId(retailCustomer.getId());
    }

    @Test
    public void findByState() {
        String state = "state";

        service.findByState(state);

        verify(repository).findByState(state);
    }

    @Test
    public void persist() {
        Authorization authorization = EspiFactory.newAuthorization(EspiFactory.newRetailCustomer(),
                EspiFactory.newApplicationInformation());

        service.persist(authorization);

        verify(repository).persist(authorization);
    }

    @Test
    public void merge() {
        Authorization authorization = EspiFactory.newAuthorization(EspiFactory.newRetailCustomer(),
                EspiFactory.newApplicationInformation());

        service.persist(authorization);

        authorization.setAccessToken(UUID.randomUUID().toString());

        service.merge(authorization);

        verify(repository).merge(authorization);
    }

    @Test
    public void findByUrl_findsUsagePointAuthorization() {
        String uri = "/espi/1_1/resource/RetailCustomer/1/UsagePoint/1";
        UsagePointRepository usagePointRepository = mock(UsagePointRepository.class);
        service.setUsagePointRepository(usagePointRepository);
        UsagePoint usagePoint = EspiFactory.newUsagePoint();
        Subscription subscription = EspiFactory.newSubscription(EspiFactory.newRetailCustomer());
        Authorization authorization = new Authorization();
        subscription.setAuthorization(authorization);
        usagePoint.setSubscription(subscription);
        when(usagePointRepository.findByURI(uri)).thenReturn(usagePoint);

        assertEquals(authorization, service.findByURI(uri));
    }

}
