package org.energyos.espi.thirdparty.mocks;

import org.energyos.espi.common.service.StateService;

import java.util.UUID;

public class MockStateServiceImpl implements StateService {
    private static String state = UUID.randomUUID().toString();

    public String newState() {
        return state;
    }
}
