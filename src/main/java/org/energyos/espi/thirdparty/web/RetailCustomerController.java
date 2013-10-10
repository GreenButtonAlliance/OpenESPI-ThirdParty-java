package org.energyos.espi.thirdparty.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class RetailCustomerController {

    @RequestMapping(value = "/RetailCustomer/{retailCustomerId}/home", method = RequestMethod.GET)
    public String home() {
        return "/RetailCustomer/home";
    }
}
