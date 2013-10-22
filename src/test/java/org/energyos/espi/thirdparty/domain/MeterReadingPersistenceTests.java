package org.energyos.espi.thirdparty.domain;

import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;

public class MeterReadingPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(MeterReading.class, Entity.class);
        assertAnnotationPresent(MeterReading.class, Table.class);
    }

    @Test
    public void intervalBlocks() {
        assertAnnotationPresent(MeterReading.class, "intervalBlocks", OneToMany.class);
        assertAnnotationPresent(MeterReading.class, "intervalBlocks", LazyCollection.class);
    }

    @Test
    public void usagePoint() {
        assertAnnotationPresent(MeterReading.class, "usagePoint", ManyToOne.class);
        assertAnnotationPresent(MeterReading.class, "usagePoint", JoinColumn.class);
    }

    @Test
    public void readingType() {
        assertAnnotationPresent(MeterReading.class, "readingType", OneToOne.class);
        assertAnnotationPresent(MeterReading.class, "readingType", JoinColumn.class);
    }
}
