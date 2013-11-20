package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.thirdparty.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
@Transactional
public class ResourceRepositoryImpl implements ResourceRepository {

    @PersistenceContext
    protected EntityManager em;
    @Autowired
    private UsagePointRepository usagePointRepository;

    public void persist(IdentifiedObject resource) {
        em.persist(resource);
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return usagePointRepository.findByUUID(uuid);
    }

    public void update(UsagePoint updatedUsagePoint) {
        UsagePoint originalUsagePoint = usagePointRepository.findByUUID(updatedUsagePoint.getUUID());
        originalUsagePoint.setDescription(updatedUsagePoint.getDescription());
        originalUsagePoint.setRoleFlags(updatedUsagePoint.getRoleFlags());
        originalUsagePoint.setServiceCategory(updatedUsagePoint.getServiceCategory());
        originalUsagePoint.setStatus(updatedUsagePoint.getStatus());

        em.merge(originalUsagePoint);
    }
}
