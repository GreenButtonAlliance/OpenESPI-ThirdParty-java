package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.repository.DataCustodianRepository;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DataCustodianServiceImplTests {

    private DataCustodianServiceImpl service;
    private DataCustodianRepository repository;

    @Before
    public void before() {
        service = new DataCustodianServiceImpl();
        repository = mock(DataCustodianRepository.class);
        service.setRepository(repository);
    }


    @Test
    public void findById() {
        service.findById(99L);

        verify(repository).findById(99L);
    }

    @Test
    public void findAll() {
        service.findAll();

        verify(repository).findAll();
    }

    @Test
    public void persist() {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();

        service.persist(dataCustodian);

        verify(repository).persist(dataCustodian);
    }
}
