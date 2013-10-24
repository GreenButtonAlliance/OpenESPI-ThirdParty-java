package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.Routes;
import org.energyos.espi.thirdparty.repository.ResourceRESTRepository;
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
}
