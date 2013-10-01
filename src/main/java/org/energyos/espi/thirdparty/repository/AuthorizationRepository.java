package org.energyos.espi.thirdparty.repository;

import org.energyos.espi.thirdparty.domain.Authorization;

import java.util.List;

public interface AuthorizationRepository {

    void persist(Authorization authorization);

    List findAllByRetailCustomerId(Long retailCustomerId);

    Authorization findByState(String state);
}
