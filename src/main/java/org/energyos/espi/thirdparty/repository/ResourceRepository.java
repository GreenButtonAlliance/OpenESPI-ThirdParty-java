package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.UsagePoint;

import java.util.UUID;

public interface ResourceRepository {
    void update(UsagePoint resource);

    void persist(IdentifiedObject resource);

    UsagePoint findByUUID(UUID uuid);
}
