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

import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.repository.MeterReadingRepository;
import org.energyos.espi.thirdparty.service.MeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

@Service
public class MeterReadingServiceImpl implements MeterReadingService {
    @Autowired
    protected MeterReadingRepository repository;

    @Override
    public MeterReading findById(String meterReadingId) throws JAXBException {
        return repository.findById(meterReadingId);
    }

    public void setRepository(MeterReadingRepository repository) {
       this.repository = repository;
    }
}
