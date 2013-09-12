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

package org.energyos.espi.thirdparty.utils;


import org.energyos.espi.thirdparty.domain.ServiceCategory;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.models.atom.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class UsagePointBuilderTests {

    UsagePointBuilder builder;

    @Before
    public void before() {
       builder = new UsagePointBuilder();
    }

    @Test
    public void givenFeedWithNoEntries_returnsNull() {
        FeedType feed = new FeedType();
        assertEquals(0, builder.newUsagePoints(feed).size());
    }

    @Test
    public void givenFeedWithUsagePointEntry_returnsUsagePoint() {
        FeedType feed = newFeed("Usage Point Title");

        assertEquals(UsagePoint.class, builder.newUsagePoints(feed).get(0).getClass());
    }

    @Test
    public void givenFeedWithUsagePointEntries_returnsUsagePoints() {
        FeedType feed = newFeed("Usage Point Title", 2);

        assertEquals(2, builder.newUsagePoints(feed).size());
    }

    @Test
    public void givenFeedWithTitledUsagePointEntry_addsEntryTitleToUsagePoint() {
        String title = "Usage Point Title";
        FeedType feed = newFeed(title);

        assertEquals(title, builder.newUsagePoints(feed).get(0).getDescription());
    }

    @Test
    public void setsMRID() {
        String title = "Usage Point Title";
        FeedType feed = newFeed(title);

        assertNotNull(builder.newUsagePoints(feed).get(0).getMRID());
    }

    private FeedType newFeed(String title) {
        return newFeed(title, 1);
    }

    private FeedType newFeed(String title, int count) {
        FeedType feed = new FeedType();

        for(int i = 0; i < count; i++) {
            EntryType entryType = new EntryType();
            entryType.setTitle(title);
            entryType.setId(new IdType());
            newUsagePoint(entryType);
            feed.getEntries().add(entryType);
        }

        return feed;
    }

    private void newUsagePoint(EntryType entryType) {
        ContentType usagePointContentType = new ContentType();
        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
        usagePointContentType.setUsagePoint(usagePoint);
        entryType.setContent(usagePointContentType);
        entryType.getLinks().add(newLinkType("self", "RetailCustomer/9b6c7063/UsagePoint/01"));
        entryType.getLinks().add(newLinkType("related", "RetailCustomer/9b6c7063/UsagePoint/01/MeterReading"));
        IdType id = new IdType();
        id.setValue("1");
        entryType.setId(id);
    }

    private LinkType newLinkType(String rel, String href) {
        LinkType link = new LinkType();
        link.setRel(rel);
        link.setHref(href);

        return link;
    }
}

