package com.softvision.botanica.common.pojo.nested;

import com.softvision.botanica.common.pojo.util.Bundled;
import com.softvision.botanica.common.pojo.util.Compound;

import java.util.ArrayList;
import java.util.List;

public class PlantLocationPOJO {
    @Bundled(key = "lat")
    private double lat;

    @Bundled(key = "lng")
    private double lng;

    @Bundled(key = "description")
    private String description;

    @Bundled(key = "pictures")
    @Compound(type = PlantImagePOJO.class)
    private ArrayList<PlantImagePOJO> pictures;

    public PlantLocationPOJO() {
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDescription() {
        return description;
    }

    public List<PlantImagePOJO> getLocationImages() {
        return pictures;
    }
}