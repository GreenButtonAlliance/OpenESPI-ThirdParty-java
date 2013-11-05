package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository repository;

    @Override
    public void persist(RetailCustomer retailCustomer) {
        repository.persist(retailCustomer);
    }

    @Override
    public List<RetailCustomer> findAll() {
        return repository.findAll();
    }

    @Override
    public RetailCustomer findById(Long retailCustomerId) {
        return repository.findById(retailCustomerId);
    }

    public void setRepository(RetailCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return repository.findByUsername(username);
        } catch (EmptyResultDataAccessException x) {
            throw new UsernameNotFoundException("Unable to find user");
        }
    }
}
