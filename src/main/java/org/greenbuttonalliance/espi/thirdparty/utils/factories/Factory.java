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

package org.greenbuttonalliance.espi.thirdparty.utils.factories;

import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;

public class Factory {

	public static UsagePoint newUsagePoint() {
		UsagePoint usagePoint = new UsagePoint();

		usagePoint.setMRID("urn:uuid:7BC41774-7190-4864-841C-861AC76D46C2");
		usagePoint.setDescription("Electric meter");
		usagePoint.setServiceCategory(newServiceCategory());

		RetailCustomer retailCustomer = new RetailCustomer();
		retailCustomer.setId(88L);
		usagePoint.setRetailCustomer(retailCustomer);

		usagePoint.getMeterReadings().add(newMeterReading());

		return usagePoint;
	}

	public static MeterReading newMeterReading() {
		MeterReading meterReading = new MeterReading();

		meterReading.setId(98L);
		meterReading.setMRID("urn:uuid:F77FBF34-A09E-4EBC-9606-FF1A59A17CAE");
		meterReading.setDescription("Electricity consumption");

		return meterReading;
	}

	public static ServiceCategory newServiceCategory() {
		ServiceCategory serviceCategory = new ServiceCategory();

		serviceCategory.setKind(new Long(0L));

		return serviceCategory;
	}
}
