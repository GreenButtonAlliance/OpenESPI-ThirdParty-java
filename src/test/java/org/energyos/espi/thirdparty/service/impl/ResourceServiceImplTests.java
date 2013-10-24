package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.Routes;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
import org.energyos.espi.thirdparty.repository.ResourceRepository;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResourceServiceImplTests {

    @Test
    public void get_fetchesResource() throws JAXBException {
        ResourceRESTRepository repository = mock(ResourceRESTRepository.class);

        ResourceServiceImpl service = new ResourceServiceImpl();
        service.setResourceRESTRepository(repository);

        Authorization authorization = new Authorization();
        String url = Routes.DataCustodianRESTUsagePointGetURL;

        service.get(authorization, url);

        verify(repository).get(authorization, url);
    }

    @Test
    public void update_updatesResource() throws JAXBException {
        ResourceRepository repository = mock(ResourceRepository.class);

        ResourceServiceImpl service = new ResourceServiceImpl();
        service.setResourceRepository(repository);

        UsagePoint resource = new UsagePoint();

        service.update(resource);

        verify(repository).update(resource);
    }
}
