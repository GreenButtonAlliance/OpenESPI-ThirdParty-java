package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.IdentifiedObject;
import org.energyos.espi.thirdparty.domain.UsagePoint;

public interface ResourceService {
    IdentifiedObject get(Authorization authorization, String uri);

    void update(UsagePoint resource);
}
