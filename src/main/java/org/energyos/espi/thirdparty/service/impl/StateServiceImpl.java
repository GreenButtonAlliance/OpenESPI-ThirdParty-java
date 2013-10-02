package org.energyos.espi.thirdparty.service.impl;

import org.energyos.espi.thirdparty.service.StateService;

import java.util.UUID;

public class StateServiceImpl implements StateService {

    @Override
    public String newState() {
        return UUID.randomUUID().toString();
    }
}
