package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.MeterReading;

import javax.xml.bind.JAXBException;

public interface MeterReadingRepository {
    MeterReading findById(String meterReadingId) throws JAXBException;
}
