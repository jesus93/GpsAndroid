package com.example.locationservice;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class TrackGPS implements LocationListener {
    public LatLongPOCO latLng;

    public TrackGPS(LatLongPOCO latData){
        this.latLng = latData;
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng.latitud = location.getLatitude();
        latLng.longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}