package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.models.atom.adapters.IntervalBlockAdapter;
import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBElement;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class IntervalBlockValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        IntervalBlock intervalBlock = new IntervalBlock();

        Set<ConstraintViolation<IntervalBlock>> violations = validator.validate(intervalBlock);

        assertTrue(violations.isEmpty());
    }
}
