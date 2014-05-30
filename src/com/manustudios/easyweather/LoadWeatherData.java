package com.manustudios.easyweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class LoadWeatherData extends AsyncTask<String, Void, JSONObject> {

	private Context mContext;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private FragmentManager mFragmentMan;
	
	public LoadWeatherData(Context context, ViewPager pager, FragmentManager fm){
		mContext = context;
		mPager = pager;
		mFragmentMan = fm;
	}
	
	
	@Override
	protected JSONObject doInBackground(String... params) {
		
		String weatherUrl = params[0];
		JSONObject jsonResponse = null;
		int responseCode = -1;
		try {
			URL searchFeedUrl = new URL(weatherUrl); 
			HttpURLConnection connection = (HttpURLConnection) searchFeedUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			
			responseCode = connection.getResponseCode();
			if(responseCode == HttpURLConnection.HTTP_OK){
				Log.i("HTTP Connection", "Feed Url connected");
				//GET DATA INTO JSONObject
				
				String line;
				StringBuilder builder = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
				 builder.append(line);
				}
				connection.disconnect();
				jsonResponse = new JSONObject(builder.toString());
				Log.i("JsonGotten", "chk");
			}else{
				Log.i(MainActivity.TAG, "Code: "+responseCode);
			}
			
		} 
		catch (MalformedURLException e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		catch (IOException e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		catch (Exception e) {
			Log.e(MainActivity.TAG, "Exception caught:"+e);
		}
		
		return jsonResponse;

	}


	@Override
	protected void onPostExecute(JSONObject result) {
		loadData(result);
	}
	
	private void loadData(JSONObject weatherData){
		
		
		if(weatherData == null){
			
		}else{
			try {
				
				JSONObject city = weatherData.getJSONObject("city");
				JSONArray weatherDaily = weatherData.getJSONArray("list");
				
				JSONObject today = weatherDaily.getJSONObject(0);
				JSONObject tomorrow = weatherDaily.getJSONObject(1);
				JSONObject dayAfter = weatherDaily.getJSONObject(2);
				
				String cityName = city.getString("name");
				
				String[] temp = new String[3];
				String[] mainDesc = new String[3];
				String[] icons = new String[3];
				
				
				temp[0] = today.getJSONObject("temp").getString("day");
				temp[1] = tomorrow.getJSONObject("temp").getString("day");
				temp[2] = dayAfter.getJSONObject("temp").getString("day");
				
				mainDesc[0] = today.getJSONArray("weather").getJSONObject(0).getString("description");
				mainDesc[1] = tomorrow.getJSONArray("weather").getJSONObject(0).getString("main");
				mainDesc[2] = dayAfter.getJSONArray("weather").getJSONObject(0).getString("main");
				
				icons[0] = today.getJSONArray("weather").getJSONObject(0).getString("icon");
				icons[1] = tomorrow.getJSONArray("weather").getJSONObject(0).getString("icon");
				icons[2] = dayAfter.getJSONArray("weather").getJSONObject(0).getString("icon");
				
				Log.i("Desc 0", mainDesc[0]);
				Log.i("temp 0", temp[0]);
				Log.i("icon 0", icons[0]);
				
				//CALL VIEW PAGER ADAPTER WHICH ADAPTS SOME FRAGMENTS
				mPagerAdapter = new ScreenSlidePagerAdapter(mFragmentMan, temp, mainDesc, icons, cityName);
				mPager.setAdapter(mPagerAdapter);
				
			} catch (Exception e) {
				Log.e("Manu-Error", ""+e);
			}
			
			
		}
		
	}
	
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		
		String[] mTemp;
		String[] mDesc;
		String[] mIcons;
		String mCity;
		
		public ScreenSlidePagerAdapter(FragmentManager fragman, String[] temp, String[] Desc, String[] icons, String city) {
	        super(fragman);
	        mTemp = temp;
	        mDesc = Desc;
	        mIcons = icons;
	        mCity = city;
	    }

	    @Override
	    public Fragment getItem(int position) {		//This is called several times if THERE ARE 3 PAGES THEN IS CALLED 3 TIMES
	    	
	    	return ViewPagerFragment.create(mContext, position, mTemp, mDesc, mIcons, mCity);
	    }

	    
	    
	    @Override
	    public int getCount() {
	        return 2;
	    }
	}

}
