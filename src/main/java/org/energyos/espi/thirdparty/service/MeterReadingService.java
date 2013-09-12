package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.repository.MeterReadingRepository;

import javax.xml.bind.JAXBException;

public interface MeterReadingService {

    MeterReading findById(String s) throws JAXBException;

    void setRepository(MeterReadingRepository repository);
}
