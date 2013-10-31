package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.BatchList;

import java.util.List;


public interface BatchListRepository {
    void persist(BatchList batchList);

    List<BatchList> findAll();
}
