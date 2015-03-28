package com.softvision.botanica.common.pojo.nested;

import java.util.List;

public class PlantPOJO {
    private String name;
    private String description;
    private List<PlantLocationPOJO> locations;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PlantLocationPOJO> getLocations() {
        return locations;
    }
}
