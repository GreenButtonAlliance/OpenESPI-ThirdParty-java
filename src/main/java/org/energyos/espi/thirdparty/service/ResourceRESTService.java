package org.energyos.espi.thirdparty.service;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.UsagePoint;

public interface ResourceRESTService {
    IdentifiedObject get(Authorization authorization, String uri);

    void update(UsagePoint resource);
}
