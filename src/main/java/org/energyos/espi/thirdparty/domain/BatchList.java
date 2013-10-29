package org.energyos.espi.thirdparty.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchList implements Serializable {
    private List<String> resources = new ArrayList<>();

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
