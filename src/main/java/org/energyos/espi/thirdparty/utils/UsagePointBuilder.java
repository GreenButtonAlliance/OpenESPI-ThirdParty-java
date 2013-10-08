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

import org.energyos.espi.thirdparty.domain.*;
import org.energyos.espi.thirdparty.models.atom.ContentType;
import org.energyos.espi.thirdparty.models.atom.EntryType;
import org.energyos.espi.thirdparty.models.atom.FeedType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsagePointBuilder {

    public List<UsagePoint> newUsagePoints(FeedType feed) {
        List<UsagePoint> usagePoints = new ArrayList<>();
        EntryLookupTable lookup = new EntryLookupTable(feed.getEntries());

        associate(feed, lookup, usagePoints);

        return usagePoints;
    }

    private void associate(FeedType feed, EntryLookupTable lookup, List<UsagePoint> usagePoints) {
        for (EntryType entry : feed.getEntries()) {
            ContentType content = entry.getContent();

            if (content.getUsagePoint() != null) {
                handleUsagePoint(entry, usagePoints);
            } else if (content.getMeterReading() != null ) {
                handleMeterReading(entry, lookup);
            } else if (content.getReadingType() != null ) {
                handleReadingType(entry);
            } else if (content.getIntervalBlocks() != null) {
                handleIntervalBlocks(entry, lookup);
            } else if (content.getElectricPowerUsageSummary() != null) {
                handleElectricPowerUsageSummary(entry, lookup);
            } else if (content.getElectricPowerQualitySummary() != null) {
                handleElectricPowerQualitySummary(entry, lookup);
            }
        }
    }

    private void handleUsagePoint(EntryType entry, List<UsagePoint> usagePoints) {
        UsagePoint usagePoint = entry.getContent().getUsagePoint();

        usagePoint.setDescription(entry.getTitle());
        usagePoint.setMRID(entry.getId().getValue());

        usagePoints.add(usagePoint);
    }

    private void handleMeterReading(EntryType entry, EntryLookupTable lookup) {
        MeterReading meterReading = entry.getContent().getMeterReading();

        meterReading.setDescription(entry.getTitle());
        meterReading.setMRID(entry.getId().getValue());

        meterReading.setReadingType(findReadingType(entry, lookup));

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().getMeterReadings().add(meterReading);
    }

    private void handleReadingType(EntryType entry) {
        ReadingType readingType = entry.getContent().getReadingType();

        readingType.setDescription(entry.getTitle());
        readingType.setMRID(entry.getId().getValue());
    }

    private void handleIntervalBlocks(EntryType entry, EntryLookupTable lookup) {
        MeterReading meterReading = lookup.getUpEntry(entry).getContent().getMeterReading();

        for (IntervalBlock intervalBlock : entry.getContent().getIntervalBlocks()) {
            meterReading.addIntervalBlock(intervalBlock);
        }
    }

    private void handleElectricPowerUsageSummary(EntryType entry, EntryLookupTable lookup) {
        ElectricPowerUsageSummary electricPowerUsageSummary = entry.getContent().getElectricPowerUsageSummary();

        electricPowerUsageSummary.setDescription(entry.getTitle());
        electricPowerUsageSummary.setMRID(entry.getId().getValue());

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().addElectricPowerUsageSummary(electricPowerUsageSummary);
    }

    private void handleElectricPowerQualitySummary(EntryType entry, EntryLookupTable lookup) {
        ElectricPowerQualitySummary electricPowerQualitySummary = entry.getContent().getElectricPowerQualitySummary();

        electricPowerQualitySummary.setDescription(entry.getTitle());
        electricPowerQualitySummary.setMRID(entry.getId().getValue());

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().addElectricPowerQualitySummary(electricPowerQualitySummary);
    }

    private ReadingType findReadingType(EntryType entry, EntryLookupTable lookup) {
        for (EntryType relatedEntry : lookup.getRelatedEntries(entry)) {
            if (relatedEntry != entry) {
                return relatedEntry.getContent().getReadingType();
            }
        }
        return null;
    }
}
