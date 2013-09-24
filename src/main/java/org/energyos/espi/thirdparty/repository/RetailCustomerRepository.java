package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.RetailCustomer;

public interface RetailCustomerRepository {
    RetailCustomer findByUsername(String username);

    void persist(RetailCustomer retailCustomer);
}
