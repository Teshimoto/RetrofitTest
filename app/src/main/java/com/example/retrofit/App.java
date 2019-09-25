package com.example.retrofit;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();



        BackendModule.createInstance();


    }
}
