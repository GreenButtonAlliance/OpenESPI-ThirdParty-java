package org.energyos.espi.thirdparty.utils.factories;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.DataCustodian;
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

    public static DataCustodian newDataCustodian() {
        DataCustodian dataCustodian = new DataCustodian();
        dataCustodian.setDescription("Description" + System.currentTimeMillis());
        dataCustodian.setUrl("http://DataCustodian" + System.currentTimeMillis() + ".example.com");
        dataCustodian.setClientId("clientId" + System.currentTimeMillis());

        return dataCustodian;
    }

    public static Authorization newAuthorization(RetailCustomer retailCustomer, DataCustodian dataCustodian) {
        Authorization authorization = new Authorization();

        authorization.setAccessToken("accessToken" + System.currentTimeMillis());
        authorization.setAuthorizationServer("http://DataCustodian" + System.currentTimeMillis() + ".example.com");
        authorization.setThirdParty("thirdParty" + System.currentTimeMillis());
        authorization.setState("state" + System.currentTimeMillis());
        authorization.setRetailCustomer(retailCustomer);
        authorization.setDataCustodian(dataCustodian);

        return authorization;
    }
}
