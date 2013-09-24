package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RetailCustomerRepositoryImpl implements RetailCustomerRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public RetailCustomer findByUsername(String username) {
        return (RetailCustomer)em.createNamedQuery(RetailCustomer.QUERY_FIND_BY_USERNAME)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public void persist(RetailCustomer retailCustomer) {
       em.persist(retailCustomer);
    }
}
