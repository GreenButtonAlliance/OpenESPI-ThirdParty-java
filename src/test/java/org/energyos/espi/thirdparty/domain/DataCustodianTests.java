package org.energyos.espi.thirdparty.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertFalse;

public class DataCustodianTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        DataCustodian dataCustodian = new DataCustodian();
        dataCustodian.setDescription("Description");
        dataCustodian.setUrl("url");

        Set<ConstraintViolation<DataCustodian>> violations = validator.validate(dataCustodian);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        DataCustodian dataCustodian = new DataCustodian();

        Set<ConstraintViolation<DataCustodian>> violations = validator.validate(dataCustodian);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void id() {
        assertAnnotationPresent(DataCustodian.class, "id", Id.class);
        assertAnnotationPresent(DataCustodian.class, "id", GeneratedValue.class);
    }

    @Test
    public void url() {
        assertAnnotationPresent(DataCustodian.class, "url", NotEmpty.class);
    }

    @Test
    public void validations() {
        assertAnnotationPresent(DataCustodian.class, "description", NotEmpty.class);
    }
}
