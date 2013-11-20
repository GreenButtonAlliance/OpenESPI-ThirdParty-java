package org.energyos.espi.thirdparty.web;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.thirdparty.service.MeterReadingRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBException;
import java.security.Principal;
import java.util.UUID;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MeterReadingController extends BaseController {
    @Autowired
    private MeterReadingRESTService meterReadingService;

    @RequestMapping(value = Routes.THIRD_PARTY_METER_READINGS_SHOW, method = RequestMethod.GET)
    public String show(@PathVariable String meterReadingId, ModelMap model, Principal principal) throws JAXBException {
        RetailCustomer currentCustomer = currentCustomer(principal);
        model.put("meterReading", meterReadingService.findByUUID(currentCustomer.getId(), UUID.fromString(meterReadingId)));
        return "/meterreadings/show";
    }

    public void setMeterReadingService(MeterReadingRESTService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }
}
