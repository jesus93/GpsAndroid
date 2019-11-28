package com.example.locationservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class ClaseBaseActividad extends AppCompatActivity {
    protected String stringNombreActividad = "CLASE_BASE_ACTIVIDAD";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notify("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.notify("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.notify("onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.notify("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.notify("onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.notify("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.notify("onResume");
    }

    private void notify(String contentText) {
        String NOTIFICATION_CHANNEL_ID = "LIFECYCLE_APP_CHANNEL_01";
        NotificationManager notificationManager;

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notificaciones App", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            try {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            catch(NullPointerException e) {
                Log.e("Método notify", "Excepción: " + e.toString());
            }
        }

        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Hearty365")
                .setContentTitle("Notificación App, Clase:" + this.stringNombreActividad)
                .setContentText(contentText)
                .setContentInfo("Info");
        try {
            notificationManager.notify(/*notification id*/1, notificationBuilder.build());
        }
        catch(NullPointerException e) {
            Log.e("Método notify", "Excepción: " + e.toString());
        }
        Log.e("Método notify", this.stringNombreActividad + " - " + contentText);
    }
}