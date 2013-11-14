package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.models.atom.FeedType;
import org.energyos.espi.thirdparty.repository.MeterReadingRESTRepository;
import org.energyos.espi.thirdparty.utils.ATOMMarshaller;
import org.energyos.espi.thirdparty.utils.UsagePointBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@Repository
public class MeterReadingRESTRepositoryImpl implements MeterReadingRESTRepository {

    @Autowired
    @Qualifier("API_FEED_URL")
    private String apiFeedURL;

    @Autowired
    private UsagePointBuilder builder;

    @Autowired
    @Qualifier("repositoryTemplate")
    private RestTemplate template;

    @Autowired
    private ATOMMarshaller marshaller;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setMarshaller(ATOMMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setBuilder(UsagePointBuilder builder) {
        this.builder = builder;
    }

    @Override
    public MeterReading findByUUID(UUID uuid) throws JAXBException {
        List<UsagePoint> usagePointList = builder.newUsagePoints(unmarshallFeedType(requestUsagePoints()));

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

    private String requestUsagePoints() {
        return template.getForObject(apiFeedURL, String.class);
    }

    private FeedType unmarshallFeedType(String  xml) throws JAXBException {
        return marshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
    }

}
