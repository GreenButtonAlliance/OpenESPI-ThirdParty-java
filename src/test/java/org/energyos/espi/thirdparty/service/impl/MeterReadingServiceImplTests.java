package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.repository.MeterReadingRESTRepository;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterReadingServiceImplTests {

    private MeterReadingRESTRepository repository;
    private MeterReadingServiceImpl service;

    @Before
    public void before() {
        service = new MeterReadingServiceImpl();
        repository = mock(MeterReadingRESTRepository.class);
        service.setRepository(repository);
    }

    @Test
    public void findByUUID_returnsMeterReading() throws JAXBException {
        MeterReading meterReading = Factory.newMeterReading();

        when(repository.findByUUID(any(UUID.class))).thenReturn(meterReading);

        assertEquals(meterReading, service.findByUUID(UUID.randomUUID()));
    }
}
