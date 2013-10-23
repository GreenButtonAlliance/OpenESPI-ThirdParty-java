package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsagePointServiceImplTests {

    private UsagePointRepository repository;
    private UsagePointServiceImpl service;

    @Before
    public void before() {
        service = new UsagePointServiceImpl();
        repository = mock(UsagePointRepository.class);
        service.setRepository(repository);
    }

    @Test
    public void findAllByRetailCustomer_returnsUsagePointList() throws JAXBException {
        List<UsagePoint> usagePointList = new ArrayList<>();
        when(repository.findAllByRetailCustomerId(any(Long.class))).thenReturn(usagePointList);
        RetailCustomer retailCustomer = new RetailCustomer();
        retailCustomer.setId(1L);

        assertEquals(usagePointList, service.findAllByRetailCustomer(retailCustomer));
    }

    @Test
    public void findById_returnsUsagePoint() throws JAXBException {
        UsagePoint usagePoint = Factory.newUsagePoint();

        when(repository.findById(any(Long.class))).thenReturn(usagePoint);

        assertEquals(usagePoint, service.findById(1L));
    }

    @Test
    public void findByUUID_returnsUsagePoint() throws JAXBException {
        UsagePoint usagePoint = Factory.newUsagePoint();

        when(repository.findByUUID(any(UUID.class))).thenReturn(usagePoint);

        assertEquals(usagePoint, service.findByUUID(usagePoint.getUUID()));
    }

    @Test
    public void findByURI_returnsUsagePoint() throws JAXBException {
        String uri = "http://localhost:8080/DataCustodian/espi/1_1/resource/RetailCustomer/1/UsagePoint/1";
        UsagePoint usagePoint = Factory.newUsagePoint();
        when(repository.findByURI(uri)).thenReturn(usagePoint);

        assertEquals(usagePoint, service.findByURI(uri));
    }
}
