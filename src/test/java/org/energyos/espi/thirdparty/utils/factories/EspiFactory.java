package org.energyos.espi.thirdparty.utils.factories;

import org.energyos.espi.thirdparty.domain.RetailCustomer;

public class EspiFactory {
    private EspiFactory() {}

    public static RetailCustomer newRetailCustomer() {
        RetailCustomer retailCustomer = new RetailCustomer();
        retailCustomer.setFirstName("First" + System.currentTimeMillis());
        retailCustomer.setLastName("Last" + System.currentTimeMillis());
        retailCustomer.setUsername("username" + System.currentTimeMillis());
        retailCustomer.setPassword("koala");

        return retailCustomer;
    }
}
