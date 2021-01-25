/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.thirdparty.repository.impl;

import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.test.EspiFactory;
import org.greenbuttonalliance.espi.thirdparty.repository.UsagePointRESTRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterReadingRESTRepositoryImplTests {

    @Test
    public void findByUUID() throws Exception {
        MeterReadingRESTRepositoryImpl repository = new MeterReadingRESTRepositoryImpl();

        UsagePointRESTRepository usagePointRESTRepository = mock(UsagePointRESTRepository.class);
        repository.setUsagePointRESTRepository(usagePointRESTRepository);

        List<UsagePoint> usagePoints = new ArrayList<>();
        UsagePoint usagePoint = EspiFactory.newUsagePoint();
        usagePoints.add(usagePoint);
        when(usagePointRESTRepository.findAllByRetailCustomerId(99L)).thenReturn(usagePoints);

        MeterReading expectedMeterReading = usagePoint.getMeterReadings().get(0);
        MeterReading meterReading = repository.findByUUID(99L, expectedMeterReading.getUUID());

        assertThat(meterReading, is(expectedMeterReading));

    }
}
