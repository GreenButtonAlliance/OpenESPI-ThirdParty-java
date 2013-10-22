package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;

import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertEquals;

public class ElectricPowerUsageSummaryValidationTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ElectricPowerUsageSummary electricPowerUsageSummary = new ElectricPowerUsageSummary();

        Set<ConstraintViolation<ElectricPowerUsageSummary>> violations = validator.validate(electricPowerUsageSummary);

        assertTrue(violations.isEmpty());
    }
}
