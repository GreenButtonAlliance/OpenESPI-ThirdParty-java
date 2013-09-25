package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RetailCustomerServiceImplTests {

    @Test
    public void loadUserByUsername() {
        RetailCustomerRepository repository = mock(RetailCustomerRepository.class);
        RetailCustomerServiceImpl service = new RetailCustomerServiceImpl();
        service.setRepository(repository);

        service.loadUserByUsername("alan");

        verify(repository).findByUsername("alan");
    }

    @Test
    public void persist() {
        RetailCustomerRepository repository = mock(RetailCustomerRepository.class);
        RetailCustomerServiceImpl service = new RetailCustomerServiceImpl();
        service.setRepository(repository);
        RetailCustomer retailCustomer = new RetailCustomer();

        service.persist(retailCustomer);

        verify(repository).persist(retailCustomer);
    }
}
