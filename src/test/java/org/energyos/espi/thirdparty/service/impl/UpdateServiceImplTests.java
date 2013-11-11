package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.ResourceService;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.mockito.Mockito.*;

public class UpdateServiceImplTests {

    public UpdateServiceImpl updateService;
    public AuthorizationService authorizationService;
    public ResourceService resourceService;
    public Authorization authorization;
    public UsagePoint updateUsagePoint;
    public String uri;

    @Before
    public void before() {
        uri = Routes.buildDataCustodianRESTUsagePointGet("1", "1");
        updateUsagePoint = new UsagePoint();
        authorization = new Authorization();
        resourceService = mock(ResourceService.class);
        authorizationService = mock(AuthorizationService.class);

        when(authorizationService.findByURI(uri)).thenReturn(authorization);
        when(resourceService.get(authorization, uri)).thenReturn(updateUsagePoint);

        updateService = new UpdateServiceImpl();
        updateService.setResourceService(resourceService);
        updateService.setAuthorizationService(authorizationService);
    }

    @Test
    public void update_findsAuthorization() throws JAXBException {
        updateService.update(uri);

        verify(authorizationService).findByURI(uri);
    }

    @Test
    public void update_fetchesResource() throws JAXBException {
        updateService.update(uri);

        verify(resourceService).get(authorization, uri);
    }

    @Test
    public void update_updatesResource() throws JAXBException {
        updateService.update(uri);

        verify(resourceService).update(updateUsagePoint);
    }
}
