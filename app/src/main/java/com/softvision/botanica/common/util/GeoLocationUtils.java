package com.softvision.botanica.common.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoLocationUtils implements LocationListener {
    private static final long LOCATION_REFRESH_TIME = 2000;
    private static final float LOCATION_REFRESH_DISTANCE = 10;

    private static GeoLocationUtils instance;

    private final LocationManager locationManager;

    private Location lastKnownLocation;

    public static void lazyInitializeSingleton(Context context) {
        instance = new GeoLocationUtils(context);
    }

    public static GeoLocationUtils getInstance() {
        if(instance == null) {
            throw new RuntimeException("Call lazyInitializeSingleton first");
        }
        return instance;
    }

    private GeoLocationUtils(Context context) {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnownLocation == null) {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnownLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //NOP
    }

    @Override
    public void onProviderEnabled(String provider) {
        //NOP
    }

    @Override
    public void onProviderDisabled(String provider) {
        //NOP
    }
}
