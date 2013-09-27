package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertEquals;

public class MeterReadingTests {
    static final String XML_INPUT = "<MeterReading xmlns=\"http://naesb.org/espi\"/>";

    @Test
    public void unmarshalsMeterReading() throws JAXBException {
        MeterReading meterReading = EspiMarshaller.<MeterReading>unmarshal(XML_INPUT).getValue();

        assertEquals(MeterReading.class, meterReading.getClass());
    }
}
