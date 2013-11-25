package org.energyos.espi.thirdparty.mocks;

import org.energyos.espi.thirdparty.web.ClientRestTemplate;
import org.energyos.espi.thirdparty.web.ClientRestTemplateFactory;

public class MockClientRestTemplateFactory extends ClientRestTemplateFactory {

    public ClientRestTemplate newClientRestTemplate(String username, String password) {
        return new MockClientRestTemplate();
    }
}
