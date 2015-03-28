package com.softvision.botanica.common.pojo.nested;

import com.softvision.botanica.common.pojo.util.Bundled;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class PlantImagePOJO {
    @Bundled(key = "url")
    private String url;

    public String getUrl() {
        return url;
    }
}
