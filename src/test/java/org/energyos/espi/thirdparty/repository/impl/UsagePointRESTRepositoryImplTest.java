package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.thirdparty.BaseTest;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.energyos.espi.common.test.EspiFactory.newAuthorization;
import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UsagePointRESTRepositoryImplTest extends BaseTest {
    @Mock
    private UsagePointBuilder builder;
    @Mock
    private ATOMMarshaller atomMarshaller;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private RestTemplate template;
    private UsagePointRESTRepositoryImpl repository;

    @Before
    public void setup() {
        repository = new UsagePointRESTRepositoryImpl();
        repository.setAuthorizationService(authorizationService);
        repository.setTemplate(template);
        repository.setAtomMarshaller(atomMarshaller);
        repository.setBuilder(builder);
    }

    @Test
    public void findAllByRetailCustomerId() throws JAXBException {
        FeedType feedType = new FeedType();
        List<Authorization> authorizations = new ArrayList<>();
        authorizations.add(newAuthorization());
        List<UsagePoint> expectedList = new ArrayList<>();
        expectedList.add(newUsagePoint());

        ResponseEntity<String> response = mock(ResponseEntity.class);

        when(authorizationService.findAllByRetailCustomerId(1L)).thenReturn(authorizations);
        when(response.getBody()).thenReturn("body");
        when(template.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class))).thenReturn(response);
        when(atomMarshaller.unmarshal(any(ByteArrayInputStream.class))).thenReturn(feedType);
        when(builder.newUsagePoints(feedType)).thenReturn(expectedList);

        List<UsagePoint> usagePointList = repository.findAllByRetailCustomerId(1L);
        assertThat(usagePointList, is(expectedList));
    }
}
