package org.energyos.espi.thirdparty.web;

public class ClientRestTemplateFactory {

    public ClientRestTemplate newClientRestTemplate(String username, String password) {
        return new ClientRestTemplate(username, password);
    }
}
