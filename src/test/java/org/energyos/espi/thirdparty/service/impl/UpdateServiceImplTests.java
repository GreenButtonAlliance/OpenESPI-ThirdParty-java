package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.ResourceRESTService;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.mockito.Mockito.*;

public class UpdateServiceImplTests {

    public UpdateRESTServiceImpl updateService;
    public AuthorizationService authorizationService;
    public ResourceRESTService resourceRESTService;
    public Authorization authorization;
    public UsagePoint updateUsagePoint;
    public String uri;

    @Before
    public void before() {
        uri = Routes.buildDataCustodianRESTUsagePointGet("1", "1");
        updateUsagePoint = new UsagePoint();
        authorization = new Authorization();
        resourceRESTService = mock(ResourceRESTService.class);
        authorizationService = mock(AuthorizationService.class);

        when(authorizationService.findByURI(uri)).thenReturn(authorization);
        when(resourceRESTService.get(authorization, uri)).thenReturn(updateUsagePoint);

        updateService = new UpdateRESTServiceImpl();
        updateService.setResourceRESTService(resourceRESTService);
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

        verify(resourceRESTService).get(authorization, uri);
    }

    @Test
    public void update_updatesResource() throws JAXBException {
        updateService.update(uri);

        verify(resourceRESTService).update(updateUsagePoint);
    }
}
