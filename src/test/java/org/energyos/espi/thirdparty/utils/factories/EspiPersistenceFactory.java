package org.energyos.espi.thirdparty.utils.factories;

import org.energyos.espi.thirdparty.domain.Authorization;
import org.energyos.espi.thirdparty.domain.DataCustodian;
import org.energyos.espi.thirdparty.domain.RetailCustomer;
import org.energyos.espi.thirdparty.service.AuthorizationService;
import org.energyos.espi.thirdparty.service.DataCustodianService;
import org.energyos.espi.thirdparty.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EspiPersistenceFactory {
   @Autowired
    private DataCustodianService dataCustodianService;
    @Autowired
    private RetailCustomerService retailCustomerService;
    @Autowired
    private AuthorizationService authorizationService;

    public Authorization createAuthorization(RetailCustomer retailCustomer) {
        DataCustodian dataCustodian = EspiFactory.newDataCustodian();
        dataCustodianService.persist(dataCustodian);
        Authorization authorization = EspiFactory.newAuthorization(retailCustomer, dataCustodian);
        authorizationService.persist(authorization);

        return authorization;
    }
}
