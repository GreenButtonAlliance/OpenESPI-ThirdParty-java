package org.energyos.espi.thirdparty.service.impl;

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
}
