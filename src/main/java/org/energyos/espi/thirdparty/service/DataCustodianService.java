package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.DataCustodian;

import java.util.List;

public interface DataCustodianService {
    DataCustodian findById(Long dataCustodianId);

    List<DataCustodian> findAll();
}
