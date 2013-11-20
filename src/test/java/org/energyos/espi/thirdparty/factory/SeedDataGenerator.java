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
