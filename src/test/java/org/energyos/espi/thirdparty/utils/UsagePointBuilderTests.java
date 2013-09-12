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


import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.models.atom.*;
import org.energyos.espi.thirdparty.utils.factories.Factory;
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
        FeedType feed = newFeed();

        assertEquals(UsagePoint.class, builder.newUsagePoints(feed).get(0).getClass());
    }

    @Test
    public void givenFeedWithUsagePointEntries_returnsUsagePoints() {
        FeedType feed = newFeed(2);

        assertEquals(2, builder.newUsagePoints(feed).size());
    }

    @Test
    public void givenFeedWithTitledUsagePointEntry_addsEntryTitleToUsagePoint() {
        FeedType feed = newFeed();

        assertEquals("Usage Point Title", builder.newUsagePoints(feed).get(0).getDescription());
    }

    @Test
    public void setsMRID() {
        FeedType feed = newFeed();

        assertNotNull(builder.newUsagePoints(feed).get(0).getMRID());
    }

    private FeedType newFeed() {
        return newFeed(1);
    }

    private FeedType newFeed(int count) {
        FeedType feed = new FeedType();

        for(int i = 0; i < count; i++) {
            EntryType entryType = newEntry();
            feed.getEntries().add(entryType);
        }

        return feed;
    }

    private EntryType newEntry() {
        EntryType entryType = new EntryType();

        ContentType usagePointContentType = new ContentType();
        usagePointContentType.setUsagePoint(Factory.newUsagePoint());
        entryType.setContent(usagePointContentType);
        entryType.getLinks().add(newLinkType("self", "RetailCustomer/9b6c7063/UsagePoint/01"));
        entryType.getLinks().add(newLinkType("related", "RetailCustomer/9b6c7063/UsagePoint/01/MeterReading"));
        IdType id = new IdType();
        id.setValue("1");
        entryType.setTitle("Usage Point Title");
        entryType.setId(id);

        return entryType;
    }

    private LinkType newLinkType(String rel, String href) {
        LinkType link = new LinkType();
        link.setRel(rel);
        link.setHref(href);

        return link;
    }
}

