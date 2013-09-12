package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.repository.MeterReadingRepository;
import org.energyos.espi.thirdparty.service.MeterReadingService;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterReadingServiceImplTests {

    private MeterReadingRepository repository;
    private MeterReadingService service;

    @Before
    public void before() {
        service = new MeterReadingServiceImpl();
        repository = mock(MeterReadingRepository.class);
        service.setRepository(repository);
    }

    @Test
    public void findById_returnsMeterReading() throws JAXBException {
        MeterReading meterReading = Factory.newMeterReading();

        when(repository.findById(any(String.class))).thenReturn(meterReading);

        assertEquals(meterReading, service.findById("1"));
    }
}
