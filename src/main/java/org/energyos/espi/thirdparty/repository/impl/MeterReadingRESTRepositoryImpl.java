package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.MeterReadingRESTRepository;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.UUID;

@Repository
public class MeterReadingRESTRepositoryImpl implements MeterReadingRESTRepository {

    @Autowired
    private UsagePointRESTRepository usagePointRESTRepository;

    public void setUsagePointRESTRepository(UsagePointRESTRepository usagePointRESTRepository) {
        this.usagePointRESTRepository = usagePointRESTRepository;
    }

    @Override
    public MeterReading findByUUID(Long retailCustomerId, UUID uuid) throws JAXBException {
        List<UsagePoint> usagePointList = usagePointRESTRepository.findAllByRetailCustomerId(retailCustomerId);

        return findMeterReading(usagePointList, uuid);
    }

    private MeterReading findMeterReading(List<UsagePoint> usagePointList, UUID uuid) {
        for (UsagePoint usagePoint : usagePointList) {
            MeterReading meterReading = findMeterReadingInUsagePoint(usagePoint.getMeterReadings(), uuid);
            if (meterReading != null) {
                return meterReading;
            }
        }
        return null;
    }

    private MeterReading findMeterReadingInUsagePoint(List<MeterReading> meterReadings, UUID uuid) {
        for (MeterReading meterReading : meterReadings) {
            if (meterReading.getUUID().equals(uuid)) {
                return meterReading;
            }
        }
        return null;
    }
}
