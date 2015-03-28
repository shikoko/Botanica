package com.softvision.botanica.common.pojo.nested;

import com.softvision.botanica.common.pojo.util.Bundled;
import com.softvision.botanica.common.pojo.util.Compound;

import java.util.ArrayList;
import java.util.List;

public class PlantPOJO {
    @Bundled(key = "name")
    private String name;

    @Bundled(key = "description")
    private String description;

    @Bundled(key = "picture")
    private String picture;

    @Bundled(key = "locations")
    @Compound(type = PlantLocationPOJO.class)
    private ArrayList<PlantLocationPOJO> locations;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PlantLocationPOJO> getLocations() {
        return locations;
    }

    public String getPicture() {
        return picture;
    }
}