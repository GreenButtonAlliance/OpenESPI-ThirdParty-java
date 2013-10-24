package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.ResourceService;
import org.energyos.espi.thirdparty.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

@Service
public class UpdateServiceImpl implements UpdateService{
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ResourceService resourceService;

    public void update(String uri) throws JAXBException {
        Authorization authorization = authorizationService.findByURI(uri);
        UsagePoint resource = (UsagePoint)resourceService.get(authorization, uri);
        resourceService.update(resource);
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}
