package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.BatchList;
import org.energyos.espi.thirdparty.repository.BatchListRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.energyos.espi.thirdparty.utils.factories.EspiFactory.newBatchList;
import static org.mockito.Mockito.verify;

public class BatchListServiceImplTest {

    @Mock
    public BatchListRepository repository;

    public BatchListServiceImpl batchListService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        batchListService = new BatchListServiceImpl();
        batchListService.setRepository(repository);
    }

    @Test
    public void persist() {
        BatchList batchList = newBatchList();
        batchListService.persist(batchList);
        verify(repository).persist(batchList);
    }

    @Test
    public void findAll() {
        batchListService.findAll();
        verify(repository).findAll();
    }
}
