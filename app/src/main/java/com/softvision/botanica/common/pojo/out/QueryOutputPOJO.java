package com.softvision.botanica.common.pojo.out;

import com.softvision.botanica.common.pojo.nested.PlantPOJO;

import java.util.List;

public class QueryOutputPOJO {
    private List<PlantPOJO> plants;

    public List<PlantPOJO> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantPOJO> plants) {
        this.plants = plants;
    }
}
