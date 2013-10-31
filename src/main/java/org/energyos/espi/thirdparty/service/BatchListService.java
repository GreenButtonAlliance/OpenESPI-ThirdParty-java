package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.BatchList;

import java.util.List;

public interface BatchListService {

    void persist(BatchList batchList);

    List<BatchList> findAll();
}
