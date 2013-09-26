package org.energyos.espi.thirdparty.domain;

public class Configuration {
    public static Long THIRD_PARTY_CLIENT_ID = 1L;
    public static String[] SCOPES = new String [] { "FB=4,5,15 IntervalDuration=3600 BlockDuration=monthly HistoryLength=13", "FB=4,5,12,15,16 IntervalDuration=monthly BlockDuration=monthly HistoryLength=13" };
}
