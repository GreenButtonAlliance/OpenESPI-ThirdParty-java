package org.energyos.espi.thirdparty.domain;

import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;
import static org.junit.Assert.assertFalse;

public class AuthorizationTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Authorization authorization = EspiFactory.newAuthorization(EspiFactory.newRetailCustomer(),
                EspiFactory.newDataCustodian());

        Set<ConstraintViolation<Authorization>> violations = validator.validate(authorization);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Authorization authorization = new Authorization();

        Set<ConstraintViolation<Authorization>> violations = validator.validate(authorization);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void extendsIdentifiableObject() {
        assertTrue(Authorization.class.getSuperclass() == IdentifiedObject.class);
    }

    @Test
    public void persistence() {
        assertAnnotationPresent(Authorization.class, Entity.class);
        assertAnnotationPresent(Authorization.class, Table.class);
    }

    @Test
    public void accessToken() {
        assertAnnotationPresent(Authorization.class, "accessToken", NotEmpty.class);
    }

    @Test
    public void authorizationServer() {
        assertAnnotationPresent(Authorization.class, "authorizationServer", NotEmpty.class);
    }

    @Test
    public void thirdParty() {
        assertAnnotationPresent(Authorization.class, "thirdParty", NotEmpty.class);
    }

    @Test
    public void retailCustomer() {
        assertAnnotationPresent(Authorization.class, "retailCustomer", ManyToOne.class);
        assertAnnotationPresent(Authorization.class, "retailCustomer", JoinColumn.class);
        assertAnnotationPresent(Authorization.class, "retailCustomer", NotNull.class);
    }

    @Test
    public void state() {
        assertAnnotationPresent(Authorization.class, "state", NotEmpty.class);
    }

    @Test
    public void dataCustodian() {
        assertAnnotationPresent(Authorization.class, "dataCustodian", ManyToOne.class);
        assertAnnotationPresent(Authorization.class, "dataCustodian", JoinColumn.class);
        assertAnnotationPresent(Authorization.class, "dataCustodian", NotNull.class);
    }
}
