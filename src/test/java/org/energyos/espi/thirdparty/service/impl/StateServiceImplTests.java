package org.energyos.espi.thirdparty.service.impl;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class StateServiceImplTests {

    @Test
    public void newState_generatesRandomState() {
        StateServiceImpl service = new StateServiceImpl();

        assertNotEquals(service.newState(), service.newState());
    }

}
