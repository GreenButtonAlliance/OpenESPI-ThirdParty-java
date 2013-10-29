package org.energyos.espi.thirdparty.domain;

public class Routes {
    public static final String AuthorizationServerAuthorizationEndpoint = "/oauth/authorize";
    public static final String AuthorizationServerTokenEndpoint = "/oauth/token";

    public static final String ThirdPartyScopeSelectionScreen = "/RetailCustomer/ScopeSelection";
    public static final String ThirdPartyScopeSelectionScreenWithRetailCustomerId = "/RetailCustomer/{retailCustomerId}/ScopeSelection";
    public static final String ThirdPartyOAuthCodeCallbackURL = "/espi/1_1/OAuthCallBack";
    public static final String ThirdPartyDataCustodianList = "/RetailCustomer/{retailCustomerID}/DataCustodianList";

    public static final String DataCustodianScopeSelectionScreen = "/RetailCustomer/ScopeSelectionList";
}
