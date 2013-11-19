package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class DefaultController extends BaseController {

    @RequestMapping(Routes.DEFAULT)
    public String defaultAfterLogin(HttpServletRequest request, Principal principal) {
        if (request.isUserInRole(RetailCustomer.ROLE_CUSTODIAN)) {
            return "redirect:/custodian/home";
        } else if (request.isUserInRole(RetailCustomer.ROLE_USER)) {
            return "redirect:/RetailCustomer/" + currentCustomer(principal).getId() + "/home";
        }
        return "redirect:/home";
    }
}
