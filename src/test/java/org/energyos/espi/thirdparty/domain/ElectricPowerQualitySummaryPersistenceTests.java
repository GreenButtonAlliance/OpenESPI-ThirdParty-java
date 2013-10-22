package org.energyos.espi.thirdparty.domain;

import org.junit.Test;

import javax.persistence.*;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;

public class ElectricPowerQualitySummaryPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(ElectricPowerQualitySummary.class, Entity.class);
        assertAnnotationPresent(ElectricPowerQualitySummary.class, Table.class);
    }

    @Test
    public void usagePoint() {
        assertAnnotationPresent(ElectricPowerQualitySummary.class, "usagePoint", ManyToOne.class);
        assertAnnotationPresent(ElectricPowerQualitySummary.class, "usagePoint", JoinColumn.class);
    }
}
