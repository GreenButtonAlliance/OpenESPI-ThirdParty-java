package org.energyos.espi.thirdparty.domain;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class MeterReadingTests {
    static final String XML_INPUT = "<MeterReading xmlns=\"http://naesb.org/espi\"/>";

    @Test
    public void unmarshalsMeterReading() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MeterReading.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        MeterReading meterReading = (MeterReading) unmarshaller.unmarshal(new ByteArrayInputStream(XML_INPUT.getBytes()));

        assertEquals(MeterReading.class, meterReading.getClass());
    }
}
