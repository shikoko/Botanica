package com.softvision.botanica.activities;

import android.app.Activity;
import android.os.Bundle;

import com.softvision.botanica.R;
import com.softvision.botanica.utils.injection.InjectLayout;
import com.softvision.botanica.utils.injection.Injector;

@InjectLayout(R.layout.activity_plant)
public class PlantActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.setupActivity(this);
    }
}
