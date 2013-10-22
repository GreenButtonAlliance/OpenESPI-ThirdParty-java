package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.models.atom.adapters.IntervalBlockAdapter;
import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.hibernate.annotations.LazyCollection;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.xml.bind.JAXBElement;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertEquals;

public class IntervalBlockPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(IntervalBlock.class, Entity.class);
        assertAnnotationPresent(IntervalBlock.class, Table.class);
    }

    @Test
    public void interval() {
        assertAnnotationPresent(IntervalBlock.class, "interval", Embedded.class);
    }

    @Test
    public void intervalReadings() {
        assertAnnotationPresent(IntervalBlock.class, "intervalReadings", OneToMany.class);
        assertAnnotationPresent(IntervalBlock.class, "intervalReadings", LazyCollection.class);
    }

    @Test
    public void meterReading() {
        assertAnnotationPresent(IntervalBlock.class, "meterReading", ManyToOne.class);
        assertAnnotationPresent(IntervalBlock.class, "meterReading", JoinColumn.class);
    }
}
