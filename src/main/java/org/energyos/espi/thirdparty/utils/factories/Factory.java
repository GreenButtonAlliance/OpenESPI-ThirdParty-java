package org.energyos.espi.thirdparty.utils.factories;


import org.energyos.espi.thirdparty.domain.MeterReading;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.ServiceCategory;
import org.energyos.espi.thirdparty.domain.UsagePoint;


public class Factory {

    public static UsagePoint newUsagePoint() {
        UsagePoint usagePoint = new UsagePoint();

        usagePoint.setMRID("1");
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
        meterReading.setMRID("1");
        meterReading.setDescription("Electricity consumption");

        return meterReading;
    }

    public static ServiceCategory newServiceCategory() {
        ServiceCategory serviceCategory = new ServiceCategory();

        serviceCategory.setKind(new Long(0L));

        return serviceCategory;
    }
}
