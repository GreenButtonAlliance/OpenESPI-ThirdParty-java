/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.thirdparty.factory;

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.RetailCustomerRepository;
import org.greenbuttonalliance.espi.common.repositories.UsagePointRepository;
import org.greenbuttonalliance.espi.common.support.EspiFactory;
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
