package com.example.asd.gpsapplicationlowerversion;

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

public class MainActivity extends AppCompatActivity
{

    private TextView latitudeLabel;
    private TextView longitudeLabel;
    private TextView statusLabel;
    private Button startButton;
    private Button stopButton;

    private boolean isUpdating = false;

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private PendingIntent pendingIntent;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    FloatingActionButton actionButton;
    private Location lastLocation = null;

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
        stopButton = (Button) findViewById(R.id.button2);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void setActionListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isUpdating)
                    return;
                isUpdating = true;
                registerReceiver(broadcastReceiver, intentFilter);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isUpdating)
                    return;
                isUpdating = false;
                statusLabel.setText("OFF");
                unregisterReceiver(broadcastReceiver);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lastLocation.getLatitude(), lastLocation.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                statusLabel.setText("ON");
            }
        });

    }


    private void setupLocationManager()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final Intent intent = new Intent("locationIntent");
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        try
        {
            locationManager.requestLocationUpdates(provider, 0, 0, pendingIntent);
        }
        catch (SecurityException ex)
        {

        }
        intentFilter = new IntentFilter("locationIntent");

        broadcastReceiver = new LocationBroadcastReceiver();

    }

    private void updateInfo(Location location)
    {
        longitudeLabel.setText("Longitude: " + location.getLongitude());
        latitudeLabel.setText("Latitude: " + location.getLatitude());
    }

    private class LocationBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Location location = (Location) bundle.get(LocationManager.KEY_LOCATION_CHANGED);
            updateInfo(location);
            lastLocation = location;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdating)
        {
            registerReceiver(broadcastReceiver, intentFilter);
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);
            try {
                locationManager.requestLocationUpdates(provider, 0, 0, pendingIntent);
            }
            catch (SecurityException e)
            {}
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException exc) {

        }
    }
}




