package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.hibernate.annotations.LazyCollection;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.xml.bind.JAXBException;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertEquals;

public class ElectricPowerUsageSummaryPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, Entity.class);
        assertAnnotationPresent(ElectricPowerUsageSummary.class, Table.class);
    }

    @Test
    public void ratchetDemandPeriod() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "ratchetDemandPeriod", Embedded.class);
    }

    @Test
    public void billingPeriod() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "billingPeriod", Embedded.class);
    }

    @Test
    public void currentBillingPeriodOverAllConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentBillingPeriodOverAllConsumption", Embedded.class);
    }

    @Test
    public void currentDayLastYearNetConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayLastYearNetConsumption", Embedded.class);
    }

    @Test
    public void currentDayNetConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayNetConsumption", Embedded.class);
    }

    @Test
    public void currentDayOverallConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayOverallConsumption", Embedded.class);
    }

    @Test
    public void peakDemand() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "peakDemand", Embedded.class);
    }

    @Test
    public void previousDayLastYearOverallConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayLastYearOverallConsumption", Embedded.class);
    }

    @Test
    public void previousDayNetConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayNetConsumption", Embedded.class);
    }

    @Test
    public void previousDayOverallConsumption() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayOverallConsumption", Embedded.class);
    }

    @Test
    public void ratchetDemand() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "ratchetDemand", Embedded.class);
    }

    @Test
    public void usagePoint() {
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "usagePoint", ManyToOne.class);
        assertAnnotationPresent(ElectricPowerUsageSummary.class, "usagePoint", JoinColumn.class);
    }
}
