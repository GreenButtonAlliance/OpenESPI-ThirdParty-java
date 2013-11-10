package org.energyos.espi.thirdparty.domain;

public class Configuration {
	// TODO: this string needs to be set up from the appropriate config file variable.
    public static final String THIRD_PARTY_BASE_URL = "http://services.greenbuttondata.org/ThirdParty";
    public static final String THIRD_PARTY_CLIENT_ID = "third_party";
    public static final String THIRD_PARTY_CLIENT_PASSWORD = "secret";
    public static final String[] SCOPES = new String [] {
            "FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13",
            "FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=10"
    };
}
