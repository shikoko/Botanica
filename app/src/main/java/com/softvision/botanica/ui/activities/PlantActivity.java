package com.softvision.botanica.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.softvision.botanica.R;
import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.common.pojo.util.BundlePojoConverter;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.utils.injection.InjectLayout;
import com.softvision.botanica.utils.injection.Injector;

@InjectLayout(R.layout.activity_plant)
public class PlantActivity extends BotanicaActivity {

    public static final String PLANT_KEY = "plant_key";

    PlantPOJO plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.setupActivity(this);
        extractFromIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        extractFromIntent(intent);
    }

    private void extractFromIntent(Intent intent) {
        if(intent.hasExtra(PLANT_KEY)) {
            plant = BundlePojoConverter.bundle2Pojo(intent.getExtras().getBundle(PLANT_KEY), PlantPOJO.class);
            populateUi();
        }
    }

    private void populateUi() {
        
    }
}
