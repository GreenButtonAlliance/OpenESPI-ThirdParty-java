package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.IdentifiedObject;

public interface ResourceRESTRepository {
    IdentifiedObject get(Authorization authorization, String uri);
}
