package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;

import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertEquals;

public class MeterReadingMarshallingTests {
    @Test
    public void intervalBlocks() {
        assertAnnotationPresent(MeterReading.class, "intervalBlocks", XmlTransient.class);
    }

    @Test
    public void usagePoint() {
        assertAnnotationPresent(MeterReading.class, "usagePoint", XmlTransient.class);
    }

    @Test
    public void readingType() {
        assertAnnotationPresent(MeterReading.class, "readingType", XmlTransient.class);
    }
}
