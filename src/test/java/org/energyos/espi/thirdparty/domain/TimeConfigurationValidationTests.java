/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.EspiMarshaller;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.energyos.espi.thirdparty.support.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeConfigurationValidationTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        TimeConfiguration timeConfiguration = new TimeConfiguration();

        Set<ConstraintViolation<TimeConfiguration>> violations = validator.validate(timeConfiguration);

        assertTrue(violations.isEmpty());
    }
}