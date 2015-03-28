package com.softvision.botanica.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.softvision.botanica.R;
import com.softvision.botanica.common.pojo.nested.PlantLocationPOJO;
import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.common.pojo.util.BundlePojoConverter;
import com.softvision.botanica.common.util.GeoLocationUtils;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.ui.views.custom.TileImageView;

import java.util.List;

public class PlantActivity extends BotanicaActivity implements LocationSource {

    public static final String PLANT_KEY = "plant_key";
    private static final int GEOFENCE_GREEN = Color.argb(60, 0, 255, 0);
    private static final int GEOFENCE_GREEN_MARGIN = Color.argb(86, 0, 255, 0);

    private PlantPOJO plant;

    private MapView mapView;
    private GoogleMap map;

    private TextView plantName;
    private TextView plantDescription;

    private TextView plantUses;
    private TextView plantParts;

    private TileImageView plantImage;

    private Location myLocation;
    private List<PlantLocationPOJO> locations;

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

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        updateLocation();
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
                locations = plant.getLocations();
                populateUi();
            }
        }
    }

    private void updateLocation() {
        if (map != null) {
            map.setMyLocationEnabled(true);
            myLocation = GeoLocationUtils.getInstance().getLastKnownLocation();
            LatLng currentPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 16));
            assertLocations();
        }
    }

    private void addCircle() {
        map.addCircle(new CircleOptions().center(
                new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).radius(100).strokeColor(GEOFENCE_GREEN_MARGIN).fillColor(GEOFENCE_GREEN)
                .strokeWidth(2).zIndex(1));
    }

    private void assertLocations() {
        if (myLocation == null) {
            myLocation = GeoLocationUtils.getInstance().getLastKnownLocation();
        }
        if (myLocation != null) {
            Double distance = 0d;
            for (PlantLocationPOJO location : locations) {
                map.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLng())));

                // we have only 1 location that's why is this in the for loop
                GeoLocationUtils.centerCamera(map, myLocation.getLatitude(), myLocation.getLongitude(), location.getLat(), location.getLng(), 24);
                Double newDistance = GeoLocationUtils.calculateDistance(myLocation, location.getLat(), location.getLng(), true);
                if (newDistance > distance) {
                    distance = newDistance;
                }
            }
            addCircle();
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
