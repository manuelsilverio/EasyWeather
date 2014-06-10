package com.manustudios.easyweather;


import java.util.Currency;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.manustuios.easyweather.R;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements
LocationListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	// A request to connect to Location Services
	private LocationRequest mLocationRequest;

	// Stores the current instantiation of the location client in this object
	private LocationClient mLocationClient;

	public final static String TAG = "Error";
	private final String preWeather ="http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	private final String postWeather = "&APPID=539eead91c31634b5d7452e218cc7d39&mode=json&units=metric";
	private String mCity;
	private ViewPager mPager;

	private Context mContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
				
		mLocationClient = new LocationClient(this, this, this);

		mPager = (ViewPager) findViewById(R.id.viewPagerFather);
		mCity="London";

		String weatherRequest = preWeather + mCity + postWeather;

		LoadWeatherData weatherLoader = new LoadWeatherData(MainActivity.this, mPager, getFragmentManager());
		weatherLoader.execute(weatherRequest);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		//mLocationClient.connect(); 

		
	}

	@Override
	public void onStop() {


		// After disconnect() is called, the client is considered "dead".
		//mLocationClient.disconnect();

		super.onStop();
	}

	 
	
	public String getLocation() {

		String location ="";
		// If Google Play Services is available
		
		//if (servicesConnected()) {

			// Get the current location
			Location currentLocation = mLocationClient.getLastLocation();
			location = currentLocation.toString();
			Log.i("Location E weather", ""+currentLocation);
		//}

		return location;
	}
	
	
	
	
	
	
	private boolean servicesConnected() {
		Log.i("google play serv", "chk2");
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {

			// In debug mode, log the status
			Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			Log.i("google play serv", "The google connection was not posibble");
			return false;
		}
	}

	/**
	 * Invoked by the "Get Location" button.
	 *
	 * Calls getLastLocation() to get the current location
	 *
	 * @param v The view object associated with this method, in this case a Button.
	 */



	@Override
	public void onConnected(Bundle arg0) {
		//Location currentLocation = mLocationClient.getLastLocation();
		//mCity=getLocation();

		//String weatherRequest = preWeather + mCity + postWeather;

		//LoadWeatherData weatherLoader = new LoadWeatherData(MainActivity.this, mPager, getFragmentManager());
		//weatherLoader.execute(weatherRequest);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}





	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}




}
