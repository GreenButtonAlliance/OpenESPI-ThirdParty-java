package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.MeterReading;

import javax.xml.bind.JAXBException;
import java.util.UUID;

public interface MeterReadingService {

    MeterReading findByUUID(UUID uuid) throws JAXBException;
}
