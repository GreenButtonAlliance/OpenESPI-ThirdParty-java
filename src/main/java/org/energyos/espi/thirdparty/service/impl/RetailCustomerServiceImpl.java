package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void persist(RetailCustomer retailCustomer) {
        repository.persist(retailCustomer);
    }

    public void setRepository(RetailCustomerRepository repository) {
        this.repository = repository;
    }
}
