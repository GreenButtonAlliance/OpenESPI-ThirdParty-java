package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.energyos.espi.thirdparty.repository.ResourceRepository;
import org.energyos.espi.thirdparty.service.ResourceRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRESTServiceImpl implements ResourceRESTService {
    @Autowired
    private ResourceRESTRepository resourceRESTRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public IdentifiedObject get(Authorization authorization, String uri) {
        return resourceRESTRepository.get(authorization, uri);
    }

    @Override
    public void update(UsagePoint resource) {
        resourceRepository.update(resource);
    }

    public void setResourceRESTRepository(ResourceRESTRepository resourceRESTRepository) {
        this.resourceRESTRepository = resourceRESTRepository;
    }

    public void setResourceRepository(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
}
