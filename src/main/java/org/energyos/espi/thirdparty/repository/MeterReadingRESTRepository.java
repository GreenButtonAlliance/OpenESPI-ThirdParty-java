package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.MeterReading;

import javax.xml.bind.JAXBException;
import java.util.UUID;

public interface MeterReadingRESTRepository {
    MeterReading findByUUID(UUID uuid) throws JAXBException;
}
