package org.energyos.espi.thirdparty.service;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface RetailCustomerService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    void persist(RetailCustomer retailCustomer);

    List<RetailCustomer> findAll();

    RetailCustomer findById(Long retailCustomerId);
}
