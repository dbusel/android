package com.example.asd.mylocationapplication;

import java.security.Security;
import java.util.Date;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {
	private TextView latitudeLabel;
	private TextView longitudeLabel;
	private TextView statusLabel;
	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		latitudeLabel = (TextView) findViewById(R.id.latitudeLabel);
		longitudeLabel = (TextView) findViewById(R.id.longitudeLabel);
		statusLabel = (TextView) findViewById(R.id.statusLabel);

		locationManager = (LocationManager) getSystemService(Activity.LOCATION_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// construct a criteria with best accuracy
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// get best ENABLED provider that meets the criteria
		String provider = locationManager.getBestProvider(criteria, true);
		// request the updates
		try {
			locationManager.requestLocationUpdates(provider, 0, 0, this);
		} catch (SecurityException ex){

		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		try {
			locationManager.removeUpdates(this);
		} catch (SecurityException ex) {

		}
	}

	public void onLocationChanged(Location location) {
		statusLabel.setText("Location recieved at " + new Date());
		latitudeLabel.setText("Latitude: " + location.getLatitude());
		longitudeLabel.setText("Longitude: " + location.getLongitude());
	}

	public void onProviderDisabled(String provider) {
		// do nothing
	}

	public void onProviderEnabled(String provider) {
		// do nothing
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// do nothing
	}
}