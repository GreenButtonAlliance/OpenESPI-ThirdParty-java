package org.energyos.espi.thirdparty.service;

import org.energyos.espi.common.domain.MeterReading;

import javax.xml.bind.JAXBException;
import java.util.UUID;

public interface MeterReadingRESTService {

    MeterReading findByUUID(UUID uuid) throws JAXBException;
}
