package org.energyos.espi.thirdparty.web;

import org.energyos.espi.thirdparty.service.MeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBException;

@Controller
@RequestMapping("/meterreadings")
public class MeterReadingController {
    @Autowired
    private MeterReadingService meterReadingService;

    @RequestMapping(value = "{meterReadingId}/show", method = RequestMethod.GET)
    public String show(@PathVariable String meterReadingId, ModelMap model) throws JAXBException {
        model.put("meterReading", meterReadingService.findById(meterReadingId));
        return "/meterreadings/show";
    }

    public void setMeterReadingService(MeterReadingService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }
}
