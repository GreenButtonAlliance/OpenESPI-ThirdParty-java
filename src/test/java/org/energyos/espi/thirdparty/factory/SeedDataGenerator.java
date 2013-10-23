package org.energyos.espi.thirdparty.factory;

import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.domain.UsagePoint;
import org.energyos.espi.thirdparty.repository.RetailCustomerRepository;
import org.energyos.espi.thirdparty.repository.UsagePointRepository;
import org.energyos.espi.thirdparty.utils.factories.EspiFactory;
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
