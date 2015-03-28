package com.softvision.botanica.common.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.softvision.botanica.R;
import com.softvision.botanica.ui.utils.UiUtils;

public class GeoLocationUtils implements LocationListener {
    private static final long LOCATION_REFRESH_TIME = 2000;
    private static final float LOCATION_REFRESH_DISTANCE = 10;
    private static int DEFAULT_PADDING = 40;
    private static final Double MILES_IN_ONE_METER = 0.000621371192;

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

    /**
     * Center map camera to include 2 specified coordinates
     *
     * @param map the map to center to include the 2 coordinates
     * @param lat1 latitude of the first coordinate
     * @param lng1 longitude of the first coordinate
     * @param lat2 latitude of the second coordinate
     * @param lng2 longitude of the second coordinate
     */
    public static void centerCamera(GoogleMap map, double lat1, double lng1, double lat2, double lng2) {
        centerCamera(map, lat1, lng1, lat2, lng2, UiUtils.getPx(DEFAULT_PADDING));
    }

    /**
     * Center map camera to include 2 specified coordinates
     *
     * @param map the map to center to include the 2 coordinates
     * @param lat1 latitude of the first coordinate
     * @param lng1 longitude of the first coordinate
     * @param lat2 latitude of the second coordinate
     * @param lng2 longitude of the second coordinate
     * @param padding outside padding in pixels
     */
    public static void centerCamera(final GoogleMap map, final double lat1, final double lng1,
                                    final double lat2, final double lng2, final int padding) {
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(lat1, lng1));
                builder.include(new LatLng(lat2, lng2));

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), padding);
                map.moveCamera(cu);
                map.setOnCameraChangeListener(null);
            }
        });
    }

    /**
     * calculate distance between current location and destination point
     *
     * @param currentLocation the current location
     * @param latitude destinations latitude
     * @param longitude destinations longitude
     * @return distance in miles
     */
    public static Double calculateDistance(Location currentLocation, Double latitude, Double longitude) {
        return calculateDistance(currentLocation, latitude, longitude, false);
    }

    /**
     * calculate distance between current location and destination point
     *
     * @param currentLocation the current location
     * @param latitude destinations latitude
     * @param longitude destinations longitude
     * @param inMeters the value of the result
     * @return distance in value depending on the inMeters flag
     */
    public static Double calculateDistance(Location currentLocation, Double latitude, Double longitude, boolean inMeters) {
        if (latitude == null || longitude == null || currentLocation == null) {
            return 0d;
        }
        Location destination = new Location(String.valueOf(R.string.app_name));
        destination.setLatitude(latitude);
        destination.setLongitude(longitude);
        // return convert to miles
        if (inMeters) {
            return destination.distanceTo(currentLocation) * 1d;
        } else {
            return destination.distanceTo(currentLocation) * MILES_IN_ONE_METER;
        }
    }
}
