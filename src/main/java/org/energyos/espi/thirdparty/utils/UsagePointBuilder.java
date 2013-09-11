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
import org.energyos.espi.thirdparty.models.atom.ContentType;
import org.energyos.espi.thirdparty.models.atom.EntryType;
import org.energyos.espi.thirdparty.models.atom.FeedType;
import org.energyos.espi.thirdparty.models.atom.LinkType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UsagePointBuilder {
    public List<UsagePoint> newUsagePointList(FeedType feed) {
        Map<String, Object> lookup = new HashMap<String, Object>();

        addSelfLinks(feed, lookup);
        addRelatedLinks(feed, lookup);
        associate(feed, lookup);

        return findUsagePoints(feed);
    }

    private void associateWithParent(Map<String, Object> lookup, ContentType content, LinkType upLink) {
        if (content.getMeterReading() != null) {
            ((EntryType) lookup.get(upLink.getHref())).getContent().getUsagePoint().getMeterReadings().add(content.getMeterReading());
        }
    }

    private List<UsagePoint> findUsagePoints(FeedType feed) {
        List<UsagePoint> usagePointList = new ArrayList<>();

        for(EntryType entryType : feed.getEntries()) {
            UsagePoint usagePoint = entryType.getContent().getUsagePoint();
            if (usagePoint != null) {
                usagePoint.setDescription(entryType.getTitle());
                usagePointList.add(usagePoint);
            }
        }
        return usagePointList;
    }

    private void associate(FeedType feed, Map<String, Object> lookup) {
        for(EntryType entryType : feed.getEntries()) {
            associateWithParent(lookup, entryType.getContent(), findUpLink(entryType));
        }
    }

    private void addRelatedLinks(FeedType feed, Map<String, Object> lookup) {
        for(EntryType entry : feed.getEntries()) {
            for(LinkType link : entry.getLinks()){
                if(link.getRel().equals("related") && !lookup.containsKey(link.getHref())){
                    lookup.put(link.getHref(), entry);
                }
            }
        }
    }

    private void addSelfLinks(FeedType feed, Map<String, Object> lookup) {
        for(EntryType entry : feed.getEntries()) {
            lookup.put(findSelfLink(entry).getHref(), entry);
        }
    }

    private LinkType findUpLink(EntryType entryType) {
        LinkType selfLink = null;
        for (LinkType link : entryType.getLinks()) {
            if (link.getRel().equals("up")) {
                selfLink = link;
                break;
            }
        }
        return selfLink;
    }

    private LinkType findSelfLink(EntryType entryType) {
        LinkType selfLink = null;
        for (LinkType link : entryType.getLinks()) {
            if (link.getRel().equals("self")) {
                selfLink = link;
                break;
            }
        }
        return selfLink;
    }
}
