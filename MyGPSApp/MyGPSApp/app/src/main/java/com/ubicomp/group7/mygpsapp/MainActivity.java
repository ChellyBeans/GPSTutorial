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

    // Step 4: ADD private locationManager and locationListener variables



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbtn = (Button)findViewById(R.id.StartButton);
        stopbtn = (Button)findViewById(R.id.StopButton);
        coordinateview = (TextView)findViewById(R.id.CoordView);

        // Step 5: GET location manager from system services


        // Step 6: Create a new locationListener
        // Step 9: in onProviderDisabled method start activity w/ Settings.ACTION_LOCATION_SOURCE_SETTINGS intent
        // Step 10: in onLocationChanged method Addend new coordinates to coordinateview


        RequestPermissions();

    }

    // STEP 2: Request Permissions you need (Methods RequestPermissions & onRequestPermissionsResult)
    // This is filled in already for you
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
            // Step 7: Start GPS w/ locationManager
        }
        else
        {
            RequestPermissions();
        }
    }

    public void stopGPS()
    {
        // Step 8: Stop GPS w/ locationManager by removing locationListener
    }

}
