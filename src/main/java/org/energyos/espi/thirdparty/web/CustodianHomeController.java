package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(Routes.DATA_CUSTODIAN_HOME)
public class CustodianHomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "/custodian/home";
    }
}
