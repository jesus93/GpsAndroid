package com.example.locationservice;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LocationFileWriter {

    private File file;
    OutputStreamWriter outStreamWriter = null;
    FileOutputStream outStream = null;
    String separator;

    public LocationFileWriter(String fileName) {
        separator = System.getProperty("line.separator");
        file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName +".csv");
        try {
            outStream = new FileOutputStream(file, true);
            outStreamWriter = new OutputStreamWriter(outStream);
        }catch(Exception e){

        }
    }

    public void writeToFile(String content) {
        try {
            createFile();
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
//            bw.write(content);
//            bw.close();
            outStreamWriter.append(content);
            outStreamWriter.append(separator);
            outStreamWriter.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createFile() {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
