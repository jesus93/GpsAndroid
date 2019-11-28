package com.example.locationservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ServiceLocation extends Service {
    Looper serviceLooper;
    ServiceHandler serviceHandler;
    Handler handlerDisplay;

    public int time = 0;
    public float distance = 0.0f;

    LocationPrinter locationPrinter = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show();
        Log.d("onDestroy", "Invocación método onDestroy");
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceHandler",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

        handlerDisplay = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();

        ArrayList params = intent.getStringArrayListExtra("parametros");
        time = Integer.parseInt(params.get(0).toString());
        distance = Float.parseFloat(params.get(1).toString());
        locationPrinter = new LocationPrinter(this, new LocationPrinterParameters(time, distance, "ActivityLocation"));
        locationPrinter.execute();
        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    class DisplayToast implements Runnable {
        private final Context context;
        String stringText;

        DisplayToast(Context context, String text) {
            this.context = context;
            this.stringText = text;
        }

        public void run() {
            Toast.makeText(context, stringText, Toast.LENGTH_SHORT).show();
        }
    }

    // Handler that receives messages from the thread
    final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Log.d("handleMessage", "Ejecutando servicio:" + msg.arg1);
                handlerDisplay.post(new DisplayToast(getApplicationContext(),
                        "Ejecutando servicio..."));
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }
}
