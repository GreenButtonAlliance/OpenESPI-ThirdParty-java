package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.DataCustodian;

import java.util.List;

public interface DataCustodianRepository {
    List<DataCustodian> findAll();

    void persist(DataCustodian dataCustodian);
}
