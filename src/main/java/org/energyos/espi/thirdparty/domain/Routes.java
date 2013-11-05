package org.energyos.espi.thirdparty.domain;

public class Routes {
    public static final String AuthorizationServerAuthorizationEndpoint = "/oauth/authorize";
    public static final String AuthorizationServerTokenEndpoint = "/oauth/token";

    public static final String ThirdPartyScopeSelectionScreen = "/RetailCustomer/ScopeSelection";
    public static final String ThirdPartyScopeSelectionScreenWithRetailCustomerId = "/RetailCustomer/{retailCustomerId}/ScopeSelection";
    public static final String ThirdPartyOAuthCodeCallbackURL = "/espi/1_1/OAuthCallBack";
    public static final String ThirdPartyAuthorizationURL = "/RetailCustomer/{retailCustomerID}/AuthorizationList";
    public static final String ThirdPartyDataCustodianList = "/RetailCustomer/{retailCustomerID}/DataCustodianList";
    public static final String ThirdPartyNotification = "/espi/1_1/Notification";

    public static final String DataCustodianScopeSelectionScreen = "/RetailCustomer/ScopeSelectionList";
    public static final String DataCustodianRESTUsagePointGetURL = "/espi/1_1/resource/RetailCustomer/{RetailCustomerID}/UsagePoint/{UsagePointID}";

    public static String getDataCustodianRESTUsagePointGetURL(String retailCustomerId, String usagePointId) {
        return DataCustodianRESTUsagePointGetURL.replace("{RetailCustomerID}", retailCustomerId).replace("{UsagePointID}", usagePointId);
    }

    public static String AuthorizationsURL(String retailCustomerID) {
        return ThirdPartyAuthorizationURL.replace("{retailCustomerID}", retailCustomerID);
    }
}
