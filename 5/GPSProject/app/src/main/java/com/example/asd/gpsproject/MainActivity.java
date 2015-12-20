package com.example.asd.gpsproject;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{

    private TextView latitudeLabel;
    private TextView longitudeLabel;
    private TextView statusLabel;
    private Button startButton;

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setActionListeners();
        setupLocationManager();
    }

    private void setupViews()
    {
        latitudeLabel = (TextView) findViewById(R.id.latitudeLabel);
        longitudeLabel = (TextView) findViewById(R.id.longitudeLabel);
        statusLabel = (TextView) findViewById(R.id.statusLabel);

        startButton = (Button) findViewById(R.id.button1);
    }

    private void setActionListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setupLocationManager();
            }
        });
    }


    private void setupLocationManager()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException ex) {

        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            statusLabel.setText("ON");
        } else {
            statusLabel.setText("OFF");
        }
    }

    private void updateInfo(Location location)
    {
        longitudeLabel.setText((int) location.getLongitude());
        latitudeLabel.setText((int) location.getLatitude());
    }

}




