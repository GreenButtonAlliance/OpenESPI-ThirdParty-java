package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.AuthorizationRepository;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthorizationServiceImplTests {

    @Test
    public void findAllByRetailCustomer() {
        AuthorizationServiceImpl service = new AuthorizationServiceImpl();
        RetailCustomer retailCustomer = new RetailCustomer();
        AuthorizationRepository repository = mock(AuthorizationRepository.class);
        service.setRepository(repository);

        service.findAllByRetailCustomerId(retailCustomer.getId());

        verify(repository).findAllByRetailCustomerId(retailCustomer.getId());
    }
}
