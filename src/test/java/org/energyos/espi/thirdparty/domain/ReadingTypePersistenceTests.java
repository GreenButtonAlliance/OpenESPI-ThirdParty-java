package org.energyos.espi.thirdparty.domain;

import org.junit.Test;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;

public class ReadingTypePersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(ReadingType.class, Entity.class);
        assertAnnotationPresent(ReadingType.class, Table.class);
    }

    @Test
    public void interhamonic() {
        assertAnnotationPresent(ReadingType.class, "interharmonic", Embedded.class);
    }

    @Test
    public void argument() {
        assertAnnotationPresent(ReadingType.class, "argument", Embedded.class);
    }
}
