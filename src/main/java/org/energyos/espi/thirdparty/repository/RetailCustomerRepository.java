package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.RetailCustomer;

import java.util.List;

public interface RetailCustomerRepository {

    List<RetailCustomer> findAll();

    RetailCustomer findByUsername(String username);

    void persist(RetailCustomer retailCustomer);

    RetailCustomer findById(Long id);
}
