package com.example.locationservice;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.lang.ref.WeakReference;

public class LocationPrinter extends AsyncTask<String, Void, Integer> {
    private WeakReference<Context> contextRef;
    private TrackGPS rastreador;
    public LocationPrinterParameters locationPrinterParams;
    public LocationManager mLocationManager;
    private LocationFileWriter locationFileWriter;
    private LatLongPOCO latLong;
    public Context mContext;

    public LocationPrinter(Context context, LocationPrinterParameters locationPrinterParameters) {
        this.contextRef = new WeakReference<>(context);
        mContext = contextRef.get();
        this.locationPrinterParams = locationPrinterParameters;
        locationFileWriter = new LocationFileWriter(locationPrinterParams.fileName);
        latLong = new LatLongPOCO(0.0,0.0);
    }

    @Override
    protected void onPreExecute() {
        rastreador = new TrackGPS(latLong);

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                locationPrinterParams.time, locationPrinterParams.distance, rastreador);
        this.latLong = rastreador.latLng;
    }

    @Override
    protected Integer doInBackground(String... params) {
        this.latLong = rastreador.latLng;
        while (latLong.latitud == 0.0) {

        }

        locationFileWriter.writeToFile("Latitud,Longitud");
        locationFileWriter.writeToFile(latLong.latitud + "," + latLong.longitude);
        double previousLati= latLong.latitud;
        double previousLongi = latLong.longitude;

        while (!isCancelled()) {
            if(latLong.latitud != previousLati || latLong.longitude != previousLongi ) {
                locationFileWriter.writeToFile(latLong.latitud + "," + latLong.longitude);
                previousLati = latLong.latitud;
                previousLongi = latLong.longitude;
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer result){

    }

    @Override
    protected void onCancelled(){
        mLocationManager.removeUpdates(rastreador);
    }

}
