package com.softvision.botanica.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.softvision.botanica.R;
import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.common.pojo.util.BundlePojoConverter;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.ui.views.custom.TileImageView;

public class PlantActivity extends BotanicaActivity implements LocationSource {

    public static final String PLANT_KEY = "plant_key";

    private PlantPOJO plant;

    private MapView mapView;
    private GoogleMap map;

    private TextView plantName;
    private TextView plantDescription;

    private TextView plantUses;
    private TextView plantParts;

    private TileImageView plantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        plantName = (TextView) findViewById(R.id.plant_name);
        plantDescription = (TextView) findViewById(R.id.plant_description);
        plantUses = (TextView) findViewById(R.id.plant_properties);
        plantParts = (TextView) findViewById(R.id.plant_parts);
        plantImage = (TileImageView) findViewById(R.id.plant_image);

        extractFromIntent(getIntent());
        setUpMap(savedInstanceState);
        MapsInitializer.initialize(getCurrentActivity());
    }

    private void setUpMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        map = ((MapView) findViewById(R.id.map)).getMap();
        if (map != null) {
            map.setMyLocationEnabled(true);
            map.setLocationSource(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        extractFromIntent(intent);
    }

    private void extractFromIntent(Intent intent) {
        if(intent.hasExtra(PLANT_KEY)) {
            plant = BundlePojoConverter.bundle2Pojo(intent.getExtras().getBundle(PLANT_KEY), PlantPOJO.class);
            if (plant != null) {
                populateUi();
            }
        }
    }

    private void populateUi() {
        plantImage.setUrl(plant.getPicture());
        plantName.setText(plant.getName() + "\n (" + plant.getBothanicalName() + ")");
        splitDescription();
        splitParts();
        splitUses();
    }

    private void splitDescription() {
        String[] descriptions = plant.getDescription().split("-");
        for (int i = 1; i < descriptions.length; i++) {
            plantDescription.append("- ");
            plantDescription.append(descriptions[i]);
            plantDescription.append("\n");
        }
    }

    private void splitParts() {
        String[] parts = plant.getParts().split("-");
        for (int i = 1; i < parts.length; i++) {
            plantParts.append("- ");
            plantParts.append(parts[i]);
            plantParts.append("\n");
        }
    }

    private void splitUses() {
        String[] uses = plant.getUses().split("-");
        for (int i = 1; i < uses.length; i++) {
            plantUses.append("- ");
            plantUses.append(uses[i]);
            plantUses.append("\n");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setUpMapIfNeeded();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

}
