/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.UsagePointRESTRepository;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.UUID;

@Service
public class UsagePointServiceImpl implements UsagePointService {

    @Autowired
    private UsagePointRESTRepository restRepository;
    @Autowired
    private UsagePointRepository repository;

    public void setRepository(UsagePointRepository repository) {
        this.repository = repository;
    }

    public void setRESTRepository(UsagePointRESTRepository restRepository) {
        this.restRepository = restRepository;
    }

    public List<UsagePoint> findAllByRetailCustomer(RetailCustomer retailCustomer) throws JAXBException {
        return repository.findAllByRetailCustomerId(retailCustomer.getId());
    }

    public UsagePoint findById(Long usagePointId) throws JAXBException {
        return repository.findById(usagePointId);
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) throws JAXBException {
        return repository.findByUUID(uuid);
    }

    @Override
    public UsagePoint findByHashedId(String usagePointHashedId) {
        return repository.findByUUID(UUID.fromString(usagePointHashedId));
    }

    @Override
    public void persist(UsagePoint usagePoint) {
        repository.persist(usagePoint);
    }

    @Override
    public UsagePoint findByURI(String uri) {
        return repository.findByURI(uri);
    }
}
