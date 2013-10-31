package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.BatchList;
import org.energyos.espi.thirdparty.repository.BatchListRepository;
import org.energyos.espi.thirdparty.service.BatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BatchListServiceImpl implements BatchListService {

    @Autowired
    private BatchListRepository repository;

    @Override
    public void persist(BatchList batchList) {
        repository.persist(batchList);
    }

    @Override
    public List<BatchList> findAll() {
        return repository.findAll();
    }

    public void setRepository(BatchListRepository repository) {
        this.repository = repository;
    }
}
