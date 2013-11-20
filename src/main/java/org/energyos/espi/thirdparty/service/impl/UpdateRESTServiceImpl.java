package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.ResourceRESTService;
import org.energyos.espi.thirdparty.service.UpdateRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

@Service
public class UpdateRESTServiceImpl implements UpdateRESTService {
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ResourceRESTService resourceRESTService;

    public void update(String uri) throws JAXBException {
        Authorization authorization = authorizationService.findByURI(uri);
        UsagePoint resource = (UsagePoint)resourceRESTService.get(authorization, uri);
        resourceRESTService.update(resource);
    }

    public void setResourceRESTService(ResourceRESTService resourceRESTService) {
        this.resourceRESTService = resourceRESTService;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
}
