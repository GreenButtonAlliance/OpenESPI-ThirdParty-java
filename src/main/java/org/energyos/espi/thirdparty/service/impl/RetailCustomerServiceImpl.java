package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void setRepository(RetailCustomerRepository repository) {
        this.repository = repository;
    }
}
