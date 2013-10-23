package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.XMLTest;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;

public class UsagePointValidationTests extends XMLTest {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        UsagePoint usagePoint = EspiFactory.newUsagePoint();

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

    @Test
    public void uri() {
        assertAnnotationPresent(UsagePoint.class, "uri", NotEmpty.class);
    }
}
