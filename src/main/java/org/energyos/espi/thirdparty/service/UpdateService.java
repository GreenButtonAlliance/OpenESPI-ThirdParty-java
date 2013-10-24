package org.energyos.espi.thirdparty.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

@Service
public interface UpdateService {
    void update(String url) throws JAXBException;
}
