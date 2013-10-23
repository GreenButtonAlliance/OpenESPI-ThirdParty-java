package org.energyos.espi.thirdparty.domain;

import org.junit.Test;

import javax.persistence.Embeddable;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;

public class SummaryMeasurementPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(SummaryMeasurement.class, Embeddable.class);
    }
}
