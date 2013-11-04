package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RetailCustomerServiceImplTests {
    private RetailCustomerRepository repository;
    private RetailCustomerServiceImpl service;

    @Before
    public void setup() {
        repository = mock(RetailCustomerRepository.class);
        service = new RetailCustomerServiceImpl();
        service.setRepository(repository);
    }

    @Test
    public void loadUserByUsername() {
        service.loadUserByUsername("alan");

        verify(repository).findByUsername("alan");
    }

    @Test
    public void persist() {
        RetailCustomer retailCustomer = new RetailCustomer();

        service.persist(retailCustomer);

        verify(repository).persist(retailCustomer);
    }

    @Test
    public void findAll_returnsAllRetailCustomers() {
        List<RetailCustomer> allRetailCustomers = new ArrayList<RetailCustomer>();

        when(repository.findAll()).thenReturn(allRetailCustomers);

        assertEquals(allRetailCustomers, service.findAll());
    }

    @Test
    public void findById_returnsRetailCustomers() {
        RetailCustomer customer = new RetailCustomer();
        customer.setId(13L);

        when(repository.findById(customer.getId())).thenReturn(customer);

        assertEquals(customer, service.findById(customer.getId()));
    }
}
