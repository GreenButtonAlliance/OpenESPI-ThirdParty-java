package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.XMLTest;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;

public class ServiceCategoryValidationTests extends XMLTest {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setKind(ServiceCategory.ELECTRICITY_SERVICE);

        Set<ConstraintViolation<ServiceCategory>> violations = validator.validate(serviceCategory);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ServiceCategory serviceCategory = new ServiceCategory();

        Set<ConstraintViolation<ServiceCategory>> violations = validator.validate(serviceCategory);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void kind() {
        assertAnnotationPresent(ServiceCategory.class, "kind", NotNull.class);
    }
}
