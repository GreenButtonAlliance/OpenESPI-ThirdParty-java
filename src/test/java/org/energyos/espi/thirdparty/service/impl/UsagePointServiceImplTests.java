package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.service.UsagePointService;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UsagePointServiceImplTests {

    private UsagePointRepository repository;
    private UsagePointService service;

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
        UsagePoint usagePoint = new UsagePoint();

        when(repository.findById(any(String.class))).thenReturn(usagePoint);

        assertEquals(usagePoint, service.findById("1"));
    }
}
