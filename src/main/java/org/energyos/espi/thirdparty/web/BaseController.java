package org.energyos.espi.thirdparty.web;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public class BaseController {
    public RetailCustomer currentCustomer(Principal principal) {
        return (RetailCustomer) ((Authentication)principal).getPrincipal();
    }
}
