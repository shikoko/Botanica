package com.softvision.botanica.ui.activities;

import android.os.Bundle;

import com.softvision.botanica.R;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.utils.injection.InjectLayout;
import com.softvision.botanica.utils.injection.Injector;

@InjectLayout(R.layout.activity_plant)
public class PlantActivity extends BotanicaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.setupActivity(this);
    }
}
