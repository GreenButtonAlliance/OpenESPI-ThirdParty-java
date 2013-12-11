package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.ServiceCategory;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.service.impl.ImportServiceImpl;
import org.energyos.espi.common.service.impl.UsagePointServiceImpl;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.hsqldb.lib.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Repository
public class UsagePointRESTRepositoryImpl implements UsagePointRESTRepository {
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate template;

    @Autowired
    UsagePointBuilder builder;

    @Autowired
    private ATOMMarshaller atomMarshaller;

    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private ImportService importService;

    @Autowired
    private UsagePointService usagePointService;
    
    @Autowired
    private UsagePointRepository usagePointRepository;
    
    @Autowired
    private RetailCustomerService retailCustomerService;
    
    // services initializers
    //
    public void setUsagePointService(UsagePointService usagePointService) {
    	this.usagePointService = usagePointService;
    }
    
    public void setImportService(ImportService importService) {
        this.importService = importService;
    }
    
    public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
        this.retailCustomerService = retailCustomerService;
    }
    
    public void setUsagePointRepository(UsagePointRepository usagePointRepository) {
    	this.usagePointRepository = usagePointRepository;
    }
    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setBuilder(UsagePointBuilder builder) {
        this.builder = builder;
    }

    public void setAtomMarshaller(ATOMMarshaller atomMarshaller) {
        this.atomMarshaller = atomMarshaller;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
	public List<UsagePoint> findAllByRetailCustomerId(Long retailCustomerId)
			throws JAXBException {

		HttpEntity<String> httpResult = getUsagePoints(findAuthorization(retailCustomerId));
		ByteArrayInputStream bs = new ByteArrayInputStream(httpResult.getBody()
				.toString().getBytes());

		try {
			importService.importData(bs);
		} catch (Exception e) {
			// TODO: need an exception handler
		}
//
        RetailCustomer retailCustomer = retailCustomerService.findById(retailCustomerId);
		List<EntryType> entries = importService.getEntries();
		// now look through the list and bind all relevant entries to the Retail Customer
		// primarily it is UsagePoints, but it may be other things in the future.
		
		for (EntryType entry : entries) {
			UsagePoint usagePoint = entry.getContent().getUsagePoint();
			if (usagePoint != null) {
				// the follow persist seems a bit heavy handed.
				usagePoint.setRetailCustomer(retailCustomer);
				usagePointRepository.createOrReplaceByUUID(usagePoint);
			}
		}
		List<UsagePoint> result;
		result = usagePointService.findAllByRetailCustomer(retailCustomer);
		return result;
	}

    @Override
    public UsagePoint findByHashedId(Long retailCustomerId, String usagePointHashedId) throws JAXBException {
        List<UsagePoint> usagePoints = findAllByRetailCustomerId(retailCustomerId);

        for (UsagePoint usagePoint : usagePoints) {
            if (usagePoint.getHashedId().equalsIgnoreCase(usagePointHashedId)) {
                return usagePoint;
            }
        }

        return null;
    }

    private FeedType unmarshalFeedType(HttpEntity<String> response) throws JAXBException {
        ByteArrayInputStream bs = new ByteArrayInputStream(response.getBody().toString().getBytes());
        //TODO: hook in the import service
        return atomMarshaller.unmarshal(bs);
    }

    private HttpEntity<String> getUsagePoints(Authorization authorization) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Bearer " + authorization.getAccessToken());
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

        return template.exchange(authorization.getSubscriptionURI(), HttpMethod.GET, requestEntity, String.class);
    }

    private Authorization findAuthorization(Long retailCustomerId) {
        List<Authorization> authorizations = authorizationService.findAllByRetailCustomerId(retailCustomerId);
        return authorizations.get(authorizations.size() - 1);
    }

}
