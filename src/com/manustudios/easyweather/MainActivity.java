package com.manustudios.easyweather;

import com.manustuios.easyweather.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity{

	public final static String TAG = "Error";
	private final String preWeather ="http://api.openweathermap.org/data/2.5/forecast/daily?q=";
	private final String postWeather = "&APPID=539eead91c31634b5d7452e218cc7d39&mode=json&units=metric";
	private String mCity;
	private ViewPager mPager;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPager = (ViewPager) findViewById(R.id.viewPagerFather);
		mCity="London";
		String weatherRequest = preWeather + mCity + postWeather;
			
		
		//GetCoord coordinatesGetter = new GetCoord();
		//coordinatesGetter.execute();
		
		
		
		LoadWeatherData weatherLoader = new LoadWeatherData(MainActivity.this, mPager, getFragmentManager());
		weatherLoader.execute(weatherRequest);
		
	}

/*
	private class GetCoord extends AsyncTask<Void, Void, Location>{

		Location location;
		private LocationManager locationManager;
		  private String provider;
		@Override
		protected Location doInBackground(Void... params) {
			try {
				// Get the location manager
			    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			    // Define the criteria how to select the locatioin provider -> use
			    // default
			    Criteria criteria = new Criteria();
			    provider = locationManager.getBestProvider(criteria, false);
			    location = locationManager.getLastKnownLocation(provider);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		    
			return location;
		}

		@Override
		protected void onPostExecute(Location result) {
			int latitude = (int) result.getLatitude();
			int longitude = (int) result.getLongitude();
			Log.i("Lat, Long",""+latitude+", "+longitude);
		}
		
		
	}
	
	*/
	

}
