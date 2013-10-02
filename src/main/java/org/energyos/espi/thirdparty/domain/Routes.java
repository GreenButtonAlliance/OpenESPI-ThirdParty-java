package org.energyos.espi.thirdparty.domain;

public class Routes {
    public static final String AuthorizationServerAuthorizationEndpoint = "/oauth/2_0/authorize";
    public static final String AuthorizationServerTokenEndpoint = "/oauth/2_0/token";

    public static final String ThirdPartyScopeAuthorization = "/RetailCustomer/ScopeAuthorization";
    public static final String ThirdPartyScopeSelectionScreen = "/RetailCustomer/ScopeSelection";
    public static final String ThirdPartyScopeSelectionScreenWithRetailCustomerId = "/espi/1_1/RetailCustomer/{retailCustomerId}/ScopeSelection";
    public static final String ThirdPartyOAuthCodeCallbackURL = "/espi/1_1/OAuthCallBack";
    public static final String ThirdPartyDataCustodianList = "/espi/1_1/RetailCustomer/{retailCustomerId}/DataCustodianList";

    public static final String DataCustodianScopeSelectionScreen = "/RetailCustomer/ScopeSelection";
}
