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

import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertSizeValidation;
import static org.junit.Assert.assertFalse;

public class RetailCustomerPersistenceTests {

    @Test
    public void id() {
        assertAnnotationPresent(RetailCustomer.class, "id", Id.class);
        assertAnnotationPresent(RetailCustomer.class, "id", GeneratedValue.class);
    }
}
