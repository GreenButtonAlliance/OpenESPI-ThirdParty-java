package org.energyos.espi.thirdparty.domain;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class UsagePointTests {
    static final String XML_INPUT = "<UsagePoint xmlns=\"http://naesb.org/espi\">" +
                                        "<ServiceCategory>" +
                                            "<kind>0</kind>" +
                                        "</ServiceCategory>" +
                                    "</UsagePoint>";

    private UsagePoint usagePoint;

    @Before
    public void before() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UsagePoint.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        usagePoint = (UsagePoint) unmarshaller.unmarshal(new ByteArrayInputStream(XML_INPUT.getBytes()));
    }

    @Test
    public void unmarshalsUsagePoint() {
        assertEquals(UsagePoint.class, usagePoint.getClass());
    }

    @Test
    public void unmarshalsServiceCategory() {
        assertEquals(ServiceCategory.class, usagePoint.getServiceCategory().getClass());
    }

    @Test
    public void unmarshalsServiceKind() {
        assertEquals(new Long(0L), usagePoint.getServiceCategory().getKind());
    }
}
