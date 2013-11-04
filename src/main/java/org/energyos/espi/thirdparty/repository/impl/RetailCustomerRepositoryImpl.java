package org.energyos.espi.thirdparty.repository.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class RetailCustomerRepositoryImpl implements RetailCustomerRepository {

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public List<RetailCustomer> findAll() {
        return (List<RetailCustomer>)this.em.createNamedQuery(RetailCustomer.QUERY_FIND_ALL).getResultList();
    }

    @Override
    public RetailCustomer findByUsername(String username) {
        return (RetailCustomer)em.createNamedQuery(RetailCustomer.QUERY_FIND_BY_USERNAME)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public RetailCustomer findById(Long id) {
        return this.em.find(RetailCustomer.class, id);
    }

    @Override
    public void persist(RetailCustomer retailCustomer) {
       em.persist(retailCustomer);
    }
}
