package org.energyos.espi.thirdparty.web.custodian;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasRole('ROLE_CUSTODIAN')")
public class RetailCustomerController {

    @Autowired
    private RetailCustomerService service;

    public void setService(RetailCustomerService service) {
        this.service = service;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new RetailCustomerValidator());
    }

    @RequestMapping(value = Routes.DATA_CUSTODIAN_RETAIL_CUSTOMER_INDEX, method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("customers", service.findAll());

        return "retailcustomers/index";
    }

    @RequestMapping(value = Routes.DATA_CUSTODIAN_RETAIL_CUSTOMER_FORM, method = RequestMethod.GET)
    public String form(ModelMap model) {
        model.put("retailCustomer", new RetailCustomer());

        return "retailcustomers/form";
    }

    @RequestMapping(value = Routes.DATA_CUSTODIAN_RETAIL_CUSTOMER_CREATE, method = RequestMethod.POST)
    public String create(@ModelAttribute("retailCustomer") @Valid RetailCustomer retailCustomer, BindingResult result) {
        if (result.hasErrors()) {
            return "retailcustomers/form";
        } else {
            service.persist(retailCustomer);
            return "redirect:/custodian/retailcustomers";
        }
    }

    @RequestMapping(value = Routes.DATA_CUSTODIAN_RETAIL_CUSTOMER_SHOW, method = RequestMethod.GET)
    public String show(@PathVariable Long retailCustomerId, ModelMap model) {
        RetailCustomer retailCustomer = service.findById(retailCustomerId);
        model.put("retailCustomer", retailCustomer);
        return "/custodian/retailcustomers/show";
    }

    public static class RetailCustomerValidator implements Validator {

        public boolean supports(Class clazz) {
            return RetailCustomer.class.isAssignableFrom(clazz);
        }

        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "Username is required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Password is required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required", "First name is required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required", "Last name is required");
        }
    }
}


