package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.IdentifiedObject;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.energyos.espi.thirdparty.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRESTRepository resourceRESTRepository;

    public IdentifiedObject get(Authorization authorization, String uri) {
        return resourceRESTRepository.get(authorization, uri);
    }

    public void setResourceRESTRepository(ResourceRESTRepository resourceRESTRepository) {
        this.resourceRESTRepository = resourceRESTRepository;
    }
}
