/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
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

package org.energyos.espi.thirdparty.factory;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.test.EspiFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SeedDataGenerator {
	@Autowired
	UsagePointRepository usagePointRepository;
	@Autowired
	RetailCustomerRepository retailCustomerRepository;

	public void init() throws Exception {
		RetailCustomer retailCustomer = EspiFactory.newRetailCustomer();
		retailCustomerRepository.persist(retailCustomer);

		UsagePoint usagePoint = EspiFactory.newUsagePoint(retailCustomer);
		usagePointRepository.persist(usagePoint);
	}
}
