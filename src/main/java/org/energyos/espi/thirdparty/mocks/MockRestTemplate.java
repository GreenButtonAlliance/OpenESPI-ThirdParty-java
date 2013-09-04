package org.energyos.espi.thirdparty.mocks;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MockRestTemplate extends RestTemplate {
    public static final String FEED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"GreenButtonDataStyleSheet.xslt\"?>\n" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\" xsi:schemaLocation=\"http://naesb.org/espi espiDerived.xsd\"\n" +
            "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "    <id>urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D</id>\n" +
            "    <title>ThirdPartyX Batch Feed</title>\n" +
            "    <updated>2012-10-24T00:00:00Z</updated>\n" +
            "    <link rel=\"self\" href=\"/ThirdParty/83e269c1/Batch\"/>\n" +
            "    <entry>\n" +
            "        <id>urn:uuid:7BC41774-7190-4864-841C-861AC76D46C2</id>\n" +
            "        <link rel=\"self\" href=\"RetailCustomer/9b6c7063/UsagePoint/01\"/>\n" +
            "        <link rel=\"up\" href=\"RetailCustomer/9b6c7063/UsagePoint\"/>\n" +
            "        <link rel=\"related\" href=\"RetailCustomer/9b6c7063/UsagePoint/01/MeterReading\"/>\n" +
            "        <link rel=\"related\" href=\"RetailCustomer/9b6c7063/UsagePoint/01/ElectricPowerUsageSummary\"/>\n" +
            "        <link rel=\"related\" href=\"LocalTimeParameters/01\"/>\n" +
            "        <title>Gas meter</title>\n" +
            "        <content>\n" +
            "            <UsagePoint xmlns=\"http://naesb.org/espi\">\n" +
            "                <ServiceCategory>\n" +
            "                    <kind>3</kind>\n" +
            "                </ServiceCategory>\n" +
            "            </UsagePoint>\n" +
            "        </content>\n" +
            "        <published>2012-10-24T00:00:00Z</published>\n" +
            "        <updated>2012-10-24T00:00:00Z</updated>\n" +
            "    </entry>\n" +
            "</feed>\n";

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        return (T)FEED;
    }
}
