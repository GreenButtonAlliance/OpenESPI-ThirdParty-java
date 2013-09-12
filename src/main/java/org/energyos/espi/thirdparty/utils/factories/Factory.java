package org.energyos.espi.thirdparty.utils.factories;


import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.ServiceCategory;
import org.energyos.espi.thirdparty.domain.UsagePoint;


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
        meterReading.setMRID("urn:uuid:E8B19EF0-6833-41CE-A28B-A5E7F9F193AE");
        meterReading.setDescription("Electricity consumption");

        return meterReading;
    }

    public static ServiceCategory newServiceCategory() {
        ServiceCategory serviceCategory = new ServiceCategory();

        serviceCategory.setKind(new Long(0L));

        return serviceCategory;
    }
}
