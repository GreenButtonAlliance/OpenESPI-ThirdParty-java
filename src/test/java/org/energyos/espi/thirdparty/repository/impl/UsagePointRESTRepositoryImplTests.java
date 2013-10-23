/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.models.atom.FeedType;
import org.energyos.espi.thirdparty.utils.ATOMMarshaller;
import org.energyos.espi.thirdparty.utils.UsagePointBuilder;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class UsagePointRESTRepositoryImplTests {

    public static final String EMPTY_FEED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><feed></feed>";
    public static final String FEED_WITH_USAGE_POINTS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><feed><entry><content><UsagePoint/></content></entry>entry><content><UsagePoint/></content></entry></feed>";

    UsagePointRESTRepositoryImpl repository;
    private RetailCustomer customer;
    private RestTemplate template;
    private ATOMMarshaller marshaller;
    private UsagePointBuilder builder;

    @Before
    public void setup() {
        repository = new UsagePointRESTRepositoryImpl();

        customer = new RetailCustomer();
        customer.setId(1L);

        template = mock(RestTemplate.class);
        repository.setTemplate(template);

        marshaller = mock(ATOMMarshaller.class);
        repository.setMarshaller(marshaller);

        builder = mock(UsagePointBuilder.class);
        repository.setBuilder(builder);
    }

    @Test
    public void findAllByRetailCustomer_givenXmlWithNoUsagePoints_returnsEmptyList() throws JAXBException {
        when(template.getForObject(any(String.class), any(Class.class))).thenReturn(EMPTY_FEED);
        when(marshaller.unmarshal(any(InputStream.class))).thenReturn(new FeedType());
        when(builder.newUsagePoints(any(FeedType.class))).thenReturn(new ArrayList<UsagePoint>());

        assertEquals(0, repository.findAllByRetailCustomerId(1L).size());
    }

    @Test
    public void findAllByRetailCustomer_givenXmlWithUsagePoints_returnsUsagePoints() throws JAXBException {
        List<UsagePoint> usagePoints = new ArrayList<UsagePoint>();
        usagePoints.add(Factory.newUsagePoint());
        usagePoints.add(Factory.newUsagePoint());

        when(template.getForObject(any(String.class), any(Class.class))).thenReturn(FEED_WITH_USAGE_POINTS);
        when(marshaller.unmarshal(any(InputStream.class))).thenReturn(new FeedType());
        when(builder.newUsagePoints(any(FeedType.class))).thenReturn(usagePoints);

        assertEquals(2, repository.findAllByRetailCustomerId(1L).size());
    }

    @Test
    public void findById_returnsUsagePoint() throws JAXBException {
        UsagePoint usagePoint = Factory.newUsagePoint();

        List<UsagePoint> usagePoints = new ArrayList<UsagePoint>();
        usagePoints.add(usagePoint);

        when(template.getForObject(any(String.class), any(Class.class))).thenReturn(FEED_WITH_USAGE_POINTS);
        when(marshaller.unmarshal(any(InputStream.class))).thenReturn(new FeedType());
        when(builder.newUsagePoints(any(FeedType.class))).thenReturn(usagePoints);

        assertEquals(usagePoint, repository.findById("7BC41774-7190-4864-841C-861AC76D46C2"));
    }

    @Test
    public void findByUUID_returnsUsagePoint() throws JAXBException {
        UsagePoint usagePoint = Factory.newUsagePoint();

        List<UsagePoint> usagePoints = new ArrayList<>();
        usagePoints.add(usagePoint);

        when(template.getForObject(any(String.class), any(Class.class))).thenReturn(FEED_WITH_USAGE_POINTS);
        when(marshaller.unmarshal(any(InputStream.class))).thenReturn(new FeedType());
        when(builder.newUsagePoints(any(FeedType.class))).thenReturn(usagePoints);

        assertEquals(usagePoint, repository.findByUUID(usagePoint.getUUID()));
    }
}
