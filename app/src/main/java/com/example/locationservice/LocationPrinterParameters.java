package com.example.locationservice;

public class LocationPrinterParameters {
    int time;
    float distance;
    String fileName;

    public LocationPrinterParameters(int time, float distance, String fileName){
        this.time = time;
        this.distance = distance;
        this.fileName = fileName;
    }
}
