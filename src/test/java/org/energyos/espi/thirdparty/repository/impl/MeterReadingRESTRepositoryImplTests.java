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

import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.thirdparty.utils.factories.Factory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterReadingRESTRepositoryImplTests {

    public static final String FEED_WITH_USAGE_POINTS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><feed><entry><content><UsagePoint/></content></entry>entry><content><UsagePoint/></content></entry></feed>";

    private MeterReadingRESTRepositoryImpl repository;
    private RestTemplate template;
    private ATOMMarshaller marshaller;
    private UsagePointBuilder builder;

    @Before
    public void setup() {
        repository = new MeterReadingRESTRepositoryImpl();

        template = mock(RestTemplate.class);
        repository.setTemplate(template);

        marshaller = mock(ATOMMarshaller.class);
        repository.setMarshaller(marshaller);

        builder = mock(UsagePointBuilder.class);
        repository.setBuilder(builder);
    }

    @Test
    public void findById_returnsMeterReading() throws JAXBException {
        UsagePoint usagePoint = Factory.newUsagePoint();

        List<UsagePoint> usagePoints = new ArrayList<>();
        usagePoints.add(usagePoint);

        when(template.getForObject(any(String.class), any(Class.class))).thenReturn(FEED_WITH_USAGE_POINTS);
        when(marshaller.unmarshal(any(InputStream.class))).thenReturn(new FeedType());
        when(builder.newUsagePoints(any(FeedType.class))).thenReturn(usagePoints);

        assertEquals(usagePoint.getMeterReadings().get(0), repository.findByUUID(UUID.fromString("E8B19EF0-6833-41CE-A28B-A5E7F9F193AE")));
    }
}
