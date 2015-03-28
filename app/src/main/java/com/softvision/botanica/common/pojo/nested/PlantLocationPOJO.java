package com.softvision.botanica.common.pojo.nested;

import java.util.List;

public class PlantLocationPOJO {
    private double lat;
    private double lng;
    private String description;
    private List<PlantImagePOJO> location_images;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDescription() {
        return description;
    }

    public List<PlantImagePOJO> getLocation_images() {
        return location_images;
    }
}
