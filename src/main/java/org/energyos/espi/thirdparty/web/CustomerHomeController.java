package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.Routes;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class CustomerHomeController {

    @RequestMapping(value = Routes.RETAIL_CUSTOMER_HOME, method = RequestMethod.GET)
    public String home() {
        return "/RetailCustomer/home";
    }
}
