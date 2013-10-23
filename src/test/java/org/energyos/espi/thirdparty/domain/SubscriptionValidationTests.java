package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertFalse;

public class SubscriptionValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Subscription subscription = new Subscription();
        subscription.setRetailCustomer(EspiFactory.newRetailCustomer());
        subscription.setUUID(UUID.randomUUID());

        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Subscription subscription = new Subscription();

        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);

        assertFalse(violations.isEmpty());
    }
    
    @Test
    public void retailCustomer() {
        assertAnnotationPresent(Subscription.class, "retailCustomer", NotNull.class);
    }
}
