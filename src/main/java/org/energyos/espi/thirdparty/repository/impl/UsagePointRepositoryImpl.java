package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class UsagePointRepositoryImpl implements UsagePointRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void persist(UsagePoint usagePoint) {
        em.persist(usagePoint);
    }

    @Override
    public List<UsagePoint> findAllByRetailCustomerId(Long id) {
        return (List<UsagePoint>) em.createNamedQuery(UsagePoint.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID)
                .setParameter("retailCustomerId", id)
                .getResultList();
    }

    @Override
    public UsagePoint findById(Long usagePointId) {
        return (UsagePoint)em.createNamedQuery(UsagePoint.QUERY_FIND_BY_ID).setParameter("usagePointId", usagePointId).getSingleResult();
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return null;
    }
}
