package org.energyos.espi.thirdparty.domain;

import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.thirdparty.XMLTest;
import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.*;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.thirdparty.support.Asserts.assertXpathValue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;

public class UsagePointMarshallingTests extends XMLTest {
    @Test
    public void roleFlags() {
        assertAnnotationPresent(UsagePoint.class, "roleFlags", XmlElement.class);
    }

    @Test
    public void serviceCategory() {
        assertAnnotationPresent(UsagePoint.class, "serviceCategory", XmlElement.class);
    }

    @Test
    public void status() {
        assertAnnotationPresent(UsagePoint.class, "status", XmlElement.class);
    }

    @Test
    public void meterReadings() {
        assertAnnotationPresent(UsagePoint.class, "meterReadings", XmlTransient.class);
    }

    @Test
    public void electricPowerQualitySummaries() {
        assertAnnotationPresent(UsagePoint.class, "electricPowerQualitySummaries", XmlTransient.class);
    }

    @Test
    public void electricPowerUsageSummaries() {
        assertAnnotationPresent(UsagePoint.class, "electricPowerUsageSummaries", XmlTransient.class);
    }

    @Test
    public void localTimeParameters() {
        assertAnnotationPresent(UsagePoint.class, "localTimeParameters", XmlTransient.class);
    }

    @Test
    public void retailCustomer() {
        assertAnnotationPresent(UsagePoint.class, "retailCustomer", XmlTransient.class);
    }

}
