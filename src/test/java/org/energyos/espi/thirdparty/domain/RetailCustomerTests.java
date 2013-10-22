package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertSizeValidation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RetailCustomerTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();

        Set<ConstraintViolation<RetailCustomer>> violations = validator.validate(retailCustomer);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        RetailCustomer retailCustomer = new RetailCustomer();

        Set<ConstraintViolation<RetailCustomer>> violations = validator.validate(retailCustomer);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void id() {
        assertAnnotationPresent(RetailCustomer.class, "id", Id.class);
        assertAnnotationPresent(RetailCustomer.class, "id", GeneratedValue.class);
    }

    @Test
    public void firstName() {
        assertAnnotationPresent(RetailCustomer.class, "firstName", NotEmpty.class);
        assertSizeValidation(RetailCustomer.class, "firstName", 2, 30);
    }

    @Test
    public void lastName() {
        assertAnnotationPresent(RetailCustomer.class, "lastName", NotEmpty.class);
        assertSizeValidation(RetailCustomer.class, "lastName", 2, 30);
    }

    @Test
    public void password() {
        assertAnnotationPresent(RetailCustomer.class, "password", NotEmpty.class);
        assertSizeValidation(RetailCustomer.class, "password", 4, 200);
    }

    @Test
    public void enabled() {
        assertAnnotationPresent(RetailCustomer.class, "enabled", NotNull.class);
    }

    @Test
    public void role() {
        assertAnnotationPresent(RetailCustomer.class, "role", NotEmpty.class);
    }
}
