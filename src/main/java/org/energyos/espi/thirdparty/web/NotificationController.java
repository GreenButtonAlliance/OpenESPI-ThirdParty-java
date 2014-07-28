/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.thirdparty.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.BatchListService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotificationController extends BaseController {

    @Autowired
    private BatchListService batchListService;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private UsagePointService usagePointService;
    
    @Autowired
    private ImportService importService;
    
    @Autowired
    @Qualifier("oAuth2RestTemplate")
    private OAuth2RestTemplate oAuth2RestTemplate;
    
    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier(value = "atomMarshaller")
    public Jaxb2Marshaller marshaller;

    @RequestMapping(value = Routes.THIRD_PARTY_NOTIFICATION, method = RequestMethod.POST)
    public void notification(HttpServletResponse response, InputStream inputStream) throws IOException {

    	BatchList batchList = (BatchList) marshaller.unmarshal(new StreamSource(inputStream));
        batchListService.persist(batchList);
        Iterator<String> it = batchList.getResources().iterator();
        while (it.hasNext()) {
        	String resourceUri = (String) it.next();
        	doImportAsynchronously(resourceUri);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }


    @Async
    private void doImportAsynchronously(String subscriptionUri) {
    	
    	// The import related to a subscription is performed here (in a separate thread)
    	// This must be provably secure b/c the access_token is visible here
        String threadName = Thread.currentThread().getName(); 
        System.out.printf("Start Asynchronous Input: %s: %s\n  ", threadName, subscriptionUri);
        
        String resourceUri = subscriptionUri;
        
        if (subscriptionUri.indexOf("?") > -1) {										// Does message contain a query element
        	resourceUri = subscriptionUri.substring(0, subscriptionUri.indexOf("?"));	// Yes, remove the query element
        }
    	if (resourceUri.contains("sftp://")) {
    		
    			try {
    				String command = "sftp mget " + resourceUri.substring(resourceUri.indexOf("sftp://"));
    				
    				System.out.println("[Manage] Restricted Management Interface");
    				System.out.println("[Manage] Request: " + command);

    				Process p = Runtime.getRuntime().exec(command);

    				// the sftp script will get the file and make a RESTful api call to add it into the workspace.

    			} catch (IOException e1)  {
    			System.out.printf("**** [Manage] Error: %s\n", e1.toString());

    		} catch (Exception e) {
    			System.out.printf("**** [Manage] Error: %s\n", e.toString());
    		}

    	} else {
        	try {
        		Authorization authorization = resourceService.findByResourceUri(resourceUri, Authorization.class);
        	   	
        		RetailCustomer retailCustomer = authorization.getRetailCustomer();

        		String accessToken = authorization.getAccessToken();
        	
        		try {

        			HttpHeaders requestHeaders = new HttpHeaders();
        			requestHeaders.set("Authorization", "Bearer " + accessToken);
        			@SuppressWarnings({ "unchecked", "rawtypes" })
        			HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
        	    
        			// get the subscription
        	   
        			HttpEntity<String> httpResult = oAuth2RestTemplate.exchange(subscriptionUri, HttpMethod.GET, requestEntity, String.class);
        	 
        			// import it into the repository
        			ByteArrayInputStream bs = new ByteArrayInputStream(httpResult.getBody()
        					.toString().getBytes());
        		
        			importService.importData(bs, retailCustomer.getId());

        		} catch (Exception e) {
        			// No Authorization, so log the fact and move on. It will 
        			// get imported later.
        			e.printStackTrace();
        			
        		}
        		   		
        	} catch (EmptyResultDataAccessException  e) {
        		// No authorization, so log the fact and move on. It will
        		// get imported later
        		e.printStackTrace();
        		
//        		System.out.printf("Asynchronous Input Completed %s: %s\n", threadName, resourceUri);
        	}
    	}  

    	
		System.out.printf("Asynchronous Input Completed %s: %s\n", threadName, resourceUri);    	
    }
    
    public void setBatchListService(BatchListService batchListService) {
        this.batchListService = batchListService;
    }

    public void setImportService(ImportService importService){
    	this.importService = importService;
    }
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }
    
    public void setUsagePointService(UsagePointService usagePointService) {
    	this.usagePointService = usagePointService;
    }
    
    public void setOAuth2RestTemplate(OAuth2RestTemplate oAuth2RestTemplate){
    	this.oAuth2RestTemplate = oAuth2RestTemplate;
    }
    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }
}
