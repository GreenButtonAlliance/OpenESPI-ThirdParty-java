package org.energyos.espi.thirdparty.domain;

public class Routes {
    public static final String AuthorizationServerAuthorizationEndpoint = "/oauth/authorize";
    public static final String AuthorizationServerTokenEndpoint = "/oauth/token";

    public static final String ThirdPartyScopeAuthorization = "/RetailCustomer/ScopeAuthorization";
    public static final String ThirdPartyScopeSelectionScreen = "/espi/1_1/RetailCustomer/ScopeSelectionList";
    public static final String ThirdPartyScopeSelectionScreenWithRetailCustomerId = "/espi/1_1/RetailCustomer/{retailCustomerId}/ScopeSelectionList";
    public static final String ThirdPartyOAuthCodeCallbackURL = "/espi/1_1/OAuthCallBack";
    public static final String ThirdPartyDataCustodianList = "/espi/1_1/RetailCustomer/{retailCustomerId}/DataCustodianList";

    public static final String DataCustodianScopeSelectionScreen = "/RetailCustomer/ScopeSelectionList";
}
