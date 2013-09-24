package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.repository.DataCustodianRepository;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DataCustodianServiceImplTests {

    @Test
    public void findAll() {
        DataCustodianServiceImpl service = new DataCustodianServiceImpl();
        DataCustodianRepository repository = mock(DataCustodianRepository.class);
        service.setRepository(repository);

        service.findAll();

        verify(repository).findAll();
    }
}
