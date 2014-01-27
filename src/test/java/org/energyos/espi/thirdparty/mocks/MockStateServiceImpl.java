package org.energyos.espi.thirdparty.mocks;

import java.util.UUID;

import org.energyos.espi.common.service.StateService;

public class MockStateServiceImpl implements StateService {
    private static String state = UUID.randomUUID().toString();

    public String newState() {
        return state;
    }
}
