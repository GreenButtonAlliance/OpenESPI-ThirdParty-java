package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.AuthorizationRepository;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private AuthorizationRepository repository;

    @Autowired
    private UsagePointRepository usagePointRepository;

    @Override
    public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
        return repository.findAllByRetailCustomerId(retailCustomerId);
    }

    @Override
    public void persist(Authorization authorization) {
        repository.persist(authorization);
    }

    @Override
    public void merge(Authorization authorization) {
        repository.merge(authorization);
    }

    @Override
    public Authorization findByURI(String uri) {
        UsagePoint usagePoint = usagePointRepository.findByURI(uri);
        return usagePoint.getSubscription().getAuthorization();
    }

    @Override
    public Authorization findByState(String state) {
        return repository.findByState(state);
    }

    public void setRepository(AuthorizationRepository repository) {
        this.repository = repository;
    }

    public void setUsagePointRepository(UsagePointRepository usagePointRepository) {
        this.usagePointRepository = usagePointRepository;
    }
}
