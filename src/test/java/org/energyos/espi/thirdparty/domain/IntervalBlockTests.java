package org.energyos.espi.thirdparty.domain;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class IntervalBlockTests {

    static final String XML_INPUT =
            "<IntervalBlock xmlns=\"http://naesb.org/espi\">" +
                "<interval>" +
                    "<duration>86400</duration>" +
                    "<start>1330578000</start>" +
                "</interval>" +
            "</IntervalBlock>";

    private IntervalBlock intervalBlock;

    @Before
    public void before() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(IntervalBlock.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        intervalBlock = (IntervalBlock) unmarshaller.unmarshal(new ByteArrayInputStream(XML_INPUT.getBytes()));
    }

    @Test
    public void unmarshallsIntervalBlock() {
        assertEquals(IntervalBlock.class, intervalBlock.getClass());
    }

    @Test
    public void unmarshal_setsIntervalDuration() {
        assertEquals(86400L,intervalBlock.getInterval().getDuration().longValue());
    }

    @Test
    public void unmarshal_setsIntervalStart() {
        assertEquals(1330578000L, intervalBlock.getInterval().getStart().longValue());
    }
}
