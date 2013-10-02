package org.energyos.espi.thirdparty.domain;

public class Configuration {
    public static final String THIRD_PARTY_BASE_URL = "http://localhost:8080/ThirdParty";
    public static final String THIRD_PARTY_CLIENT_ID = "third_party";
    public static final String THIRD_PARTY_CLIENT_PASSWORD = "secret";
    public static final String[] SCOPES = new String [] {
            "FB_4_5_15_IntervalDuration_3600_BlockDuration_monthly_HistoryLength_13",
            "FB_4_5_15_IntervalDuration_3600_BlockDuration_monthly_HistoryLength_10"
    };
}
