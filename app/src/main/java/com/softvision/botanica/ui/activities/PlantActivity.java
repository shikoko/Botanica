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
    private String[] descriptions;

    private MapView mapView;
    private GoogleMap map;

    private TextView plantName;
    private TextView plantDescription;
    private TileImageView plantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        plantName = (TextView) findViewById(R.id.plant_name);
        plantDescription = (TextView) findViewById(R.id.plant_description);
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
        splitDescription();
        plantImage.setUrl(plant.getPicture());
        plantName.setText(plant.getName());
        for (int i = 1; i < descriptions.length; i++) {
            plantDescription.append("- ");
            plantDescription.append(descriptions[i]);
            plantDescription.append("\n");
        }
    }

    private void splitDescription() {
        descriptions = plant.getDescription().split("-");
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

//    private void setUpMapIfNeeded() {
//        Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                    .getMap();
            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }

//    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//    }
}
