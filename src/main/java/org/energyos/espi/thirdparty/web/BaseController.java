package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.RetailCustomer;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class BaseController {
    public RetailCustomer currentCustomer(Principal principal) {
        if (principal == null) return null;
        return (RetailCustomer)((Authentication)principal).getPrincipal();
    }

    public boolean isUserCustodian(Principal principal) {
        return checkRole(principal, RetailCustomer.ROLE_CUSTODIAN);
    }

    public boolean isUserUserRole(Principal principal) {
        return checkRole(principal, RetailCustomer.ROLE_USER);
    }

    private boolean checkRole(Principal principal, String givenRole) {
        RetailCustomer retailCustomer = currentCustomer(principal);
        if(retailCustomer == null) return false;

        String role = retailCustomer.getRole();

        return givenRole.equals(role);
    }
}
