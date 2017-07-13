package com.ubicomp.group7.mygpsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {

    private Button startbtn, stopbtn;
    private TextView coordinateview;

    //ADD private LocationManager and Location Listener
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbtn = (Button)findViewById(R.id.StartButton);
        stopbtn = (Button)findViewById(R.id.StopButton);
        coordinateview = (TextView)findViewById(R.id.CoordView);

        //GET location manager from system services
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Create a new locationlistener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Append new coordinates to coordinateview
                coordinateview.append("\n" + location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                // start activity w/ Settings.ACTION_LOCATION_SOURCE_SETTINGS intent
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        RequestPermissions();

    }

    public void RequestPermissions()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
               requestPermissions(new String[]{
                       Manifest.permission.ACCESS_COARSE_LOCATION,
                       Manifest.permission.ACCESS_FINE_LOCATION,
                       Manifest.permission.INTERNET
               }, 10);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults)
    {
        switch(requestCode)
        {
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureStartButton();
                    configureStopButton();
                }
                break;
        }
    }

    public void configureStartButton()
    {
        startbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startGPS();
            }
        });
    }

    public void configureStopButton()
    {
        stopbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                stopGPS();
            }
        });
    }

    public void startGPS()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Start GPS w/ locationManager
            locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
        }
        else
        {
            RequestPermissions();
        }
    }

    public void stopGPS()
    {
        // Stop GPS w/ locationManager
        locationManager.removeUpdates(locationListener);
    }

}
