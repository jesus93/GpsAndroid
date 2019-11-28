package com.example.locationservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class SecondActivity extends AppCompatActivity implements
        View.OnClickListener {

    public int time = 0;
    public float distance = 0.0f;
    private AnimationDrawable animation;
    LocationPrinter locationPrinter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ArrayList params = getIntent().getStringArrayListExtra("parametros");
        time = Integer.parseInt(params.get(0).toString());
        distance = Float.parseFloat(params.get(1).toString());
        locationPrinter = new LocationPrinter(this, new LocationPrinterParameters(time, distance, "ActivityLocation"));

        try {
            locationPrinter.execute();
        } catch (Exception e) {
            locationPrinter.cancel(true);
        }

        ImageView loading = findViewById(R.id.imageView );
        animation = (AnimationDrawable) loading.getDrawable();
        animation.start();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        locationPrinter.cancel(true);
        animation.stop();
    }
}
