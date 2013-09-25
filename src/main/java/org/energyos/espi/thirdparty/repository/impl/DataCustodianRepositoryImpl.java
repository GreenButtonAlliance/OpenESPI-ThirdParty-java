package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.repository.DataCustodianRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DataCustodianRepositoryImpl implements DataCustodianRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public DataCustodian findById(Long dataCustodianId) {
        return (DataCustodian)em.createNamedQuery(DataCustodian.QUERY_FIND_BY_ID)
                .setParameter("id", dataCustodianId).getSingleResult();
    }

    @Override
    public List<DataCustodian> findAll() {
        return em.createNamedQuery(DataCustodian.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public void persist(DataCustodian dataCustodian) {
       em.persist(dataCustodian);
    }
}
