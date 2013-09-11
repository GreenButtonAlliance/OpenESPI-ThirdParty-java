package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.models.atom.FeedType;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.utils.ATOMMarshaller;
import org.energyos.espi.thirdparty.utils.UsagePointBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.List;

//@Repository
public class UsagePointRepositoryImpl implements UsagePointRepository {
//    @Autowired
//    @Qualifier("sparklrRestTemplate")
    private RestOperations template;
//    private RestOperations sparklrRestTemplate;
//    private RestOperations trustedClientRestTemplate;

    @Autowired
    @Qualifier("API_FEED_URL")
    private String apiFeedURL;

    @Autowired
    ATOMMarshaller marshaller;

    @Autowired
    UsagePointBuilder builder;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setApiFeedURL(String apiFeedURL) {
        this.apiFeedURL = apiFeedURL;
    }

    public void setMarshaller(ATOMMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setBuilder(UsagePointBuilder builder) {
        this.builder = builder;
    }

   @Override
    public List<UsagePoint> findAllByRetailCustomerId(Long id) throws JAXBException {
       return builder.newUsagePoints(unmarshallFeedType(requestUsagePoints()));
    }

    @Override
    public UsagePoint findById(String usagePointId) throws JAXBException {
        List<UsagePoint> usagePoints = builder.newUsagePoints(unmarshallFeedType(requestUsagePoints()));

        for (UsagePoint usagePoint : usagePoints) {
            if (usagePoint.getMRID().equals(usagePointId)) {
                return usagePoint;
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
