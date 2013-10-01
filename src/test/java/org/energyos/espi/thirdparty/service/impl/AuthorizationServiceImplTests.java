package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.AuthorizationRepository;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthorizationServiceImplTests {


    private AuthorizationServiceImpl service;
    private AuthorizationRepository repository;

    @Before
    public void before() {
        service = new AuthorizationServiceImpl();
        repository = mock(AuthorizationRepository.class);
        service.setRepository(repository);
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
                EspiFactory.newDataCustodian());

        service.persist(authorization);

        verify(repository).persist(authorization);
    }

}
