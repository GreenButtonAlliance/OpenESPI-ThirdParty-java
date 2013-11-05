package org.energyos.espi.thirdparty.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/custodian/home")
public class CustodianHomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/custodian/home";
    }
}
