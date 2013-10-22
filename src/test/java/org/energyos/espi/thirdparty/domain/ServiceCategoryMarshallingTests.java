package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.XMLTest;
import org.junit.Test;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;

public class ServiceCategoryMarshallingTests extends XMLTest {

    @Test
    public void kind() {
        assertAnnotationPresent(ServiceCategory.class, "kind", XmlElement.class);
    }
}
