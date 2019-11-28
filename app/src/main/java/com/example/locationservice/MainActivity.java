package com.example.locationservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public int timeUpdateLocation = 2000;
    public float distanceUpateLocation = (float)0.05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((EditText)findViewById(R.id.editTextDistance)).setText(String.valueOf(distanceUpateLocation));
        ((EditText)findViewById(R.id.editTextTime)).setText(String.valueOf(timeUpdateLocation));
        initLocationService();
    }

    @Override
    public void onClick(View v) {
        int newTimeUpdateLocation;
        float newDistanceUpdateLocation;
        try {
            newTimeUpdateLocation = Integer.parseInt(((EditText)findViewById(R.id.editTextTime)).getText().toString());
            newDistanceUpdateLocation = Float.parseFloat(((EditText)findViewById(R.id.editTextDistance)).getText().toString());
        } catch (NumberFormatException e) {
            Toast toast2 = Toast.makeText(getApplicationContext(),
                    "Ingrese un valor numérico válido.", Toast.LENGTH_SHORT);
            toast2.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
            toast2.show();
            return;
        }

        if(newTimeUpdateLocation != timeUpdateLocation || newDistanceUpdateLocation != distanceUpateLocation) {
            timeUpdateLocation = newTimeUpdateLocation;
            distanceUpateLocation = newDistanceUpdateLocation;
        }

try {

    if (((Button) v).getText().toString().equals(getString(R.string.buttonActivity_Text))) {
        Intent intent = new Intent(this, SecondActivity.class)
                .putStringArrayListExtra("parametros", new ArrayList<>(Arrays.asList(String.valueOf(timeUpdateLocation), String.valueOf(distanceUpateLocation))));
        startActivity(intent);
    } else if (((Button) v).getText().toString().toUpperCase().equals("Intent Service".toUpperCase())) {
        Toast.makeText(getApplicationContext(), "Iniciando intent service",
                Toast.LENGTH_LONG).show();
        String time = String.valueOf(timeUpdateLocation);
        String distance = String.valueOf(distanceUpateLocation);
        IntentServiceLocation intentService = new IntentServiceLocation();
        intentService.startActionLocation(this, time, distance);
    } else if (((Button) v).getText().toString().equals("Service")) {
        Intent intent = new Intent(getApplicationContext(),
                ServiceLocation.class)
                .putStringArrayListExtra("parametros", new ArrayList<>(Arrays.asList(String.valueOf(timeUpdateLocation), String.valueOf(distanceUpateLocation))));
        startService(intent);
    }
}
catch (Exception ex) {
    Toast.makeText(getApplicationContext(), "Error" + ex.getMessage(),
            Toast.LENGTH_LONG).show();

    }
    }

    private void initLocationService() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[] {
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                return;
        }
    }

//    @Override
//    public void onClick(View view) {
//        int newTimeUpdateLocation;
//        float newDistanceUpdateLocation;
//        try {
//            newTimeUpdateLocation = Integer.parseInt(((EditText)findViewById(R.id.editTextTime)).getText().toString());
//            newDistanceUpdateLocation = Float.parseFloat(((EditText)findViewById(R.id.editTextDistance)).getText().toString());
//        } catch (NumberFormatException e) {
//            Toast toast2 = Toast.makeText(getApplicationContext(),
//                    "Ingrese un valor numérico válido.", Toast.LENGTH_SHORT);
//            toast2.setGravity(Gravity.BOTTOM|Gravity.CENTER,0,0);
//            toast2.show();
//            return;
//        }
//
//        if(newTimeUpdateLocation != timeUpdateLocation || newDistanceUpdateLocation != distanceUpateLocation) {
//            timeUpdateLocation = newTimeUpdateLocation;
//            distanceUpateLocation = newDistanceUpdateLocation;
//            locationManager.removeUpdates(this);
//            //Log.e("removeUpdates", LocationManager.GPS_PROVIDER);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                    timeUpdateLocation, distanceUpateLocation, this);
//        }
//    }
}

