package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UsagePointServiceImplTests {
    @Test
    public void findAllByRetailCustomer_returnsUsagePointList() throws JAXBException {
        List<UsagePoint> usagePointList = new ArrayList<>();
        UsagePointRepository repository = mock(UsagePointRepository.class);
        when(repository.findAllByRetailCustomerId(any(Long.class))).thenReturn(usagePointList);
        UsagePointServiceImpl service = new UsagePointServiceImpl();
        service.setRepository(repository);
        RetailCustomer retailCustomer = new RetailCustomer();
        retailCustomer.setId(1L);

        assertEquals(usagePointList, service.findAllByRetailCustomer(retailCustomer));
    }
}
