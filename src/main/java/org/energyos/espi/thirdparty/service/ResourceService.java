package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.IdentifiedObject;

public interface ResourceService {
    IdentifiedObject get(Authorization authorization, String uri);
}
