package com.example.locationservice;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

public class Rastreador implements LocationListener {

    private LatLng currentLocation = null;
    private LocationManager locationManager = null;
    private Context mContext;
    private int updateTime;
    private float updateDistance;

    public Rastreador(Context context, LocationPrinterParameters updateParameters) {
        this.mContext = context;
        this.updateDistance = updateParameters.distance;
        this.updateTime = updateParameters.time;
    }

    public LatLng getLocation() {
        this.locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Log.e("rastreador", LocationManager.GPS_PROVIDER + " " + LocationManager.class);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                updateTime, updateDistance, this);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation != null) {
            currentLocation = new LatLng(myLocation.getLatitude(), myLocation.getAltitude());
        }
        return currentLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//        currentLocation = new LatLng(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
