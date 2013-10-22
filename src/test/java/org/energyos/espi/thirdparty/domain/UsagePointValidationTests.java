package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.XMLTest;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;

public class UsagePointValidationTests extends XMLTest {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");
        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));

        Set<ConstraintViolation<UsagePoint>> violations = validator.validate(usagePoint);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        UsagePoint usagePoint = new UsagePoint();

        Set<ConstraintViolation<UsagePoint>> violations = validator.validate(usagePoint);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void serviceCategory() {
        assertAnnotationPresent(UsagePoint.class, "serviceCategory", NotNull.class);
    }
}
