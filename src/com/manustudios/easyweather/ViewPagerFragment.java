package com.manustudios.easyweather;

import com.manustuios.easyweather.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ViewPagerFragment extends Fragment{

	public static final String KEY_PAGE = "page";
	private int mPageNumber;
	private Context mContext;
	
	private String[] mTemp;
	private String[] mDesc;
	private String[] mIcons;
	private String mCity;

	public static Fragment create(Context context, int pageNumber, String[] temp, String[] desc, String[] icons, String city){
		ViewPagerFragment fragment = new ViewPagerFragment(context, temp, desc, icons, city);
		
		
		
		Bundle args = new Bundle();
		args.putInt(KEY_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	
	public ViewPagerFragment(Context context, String[] temp, String[] desc, String[] icons, String city) {
		mContext = context;
		mTemp = temp;
		mDesc = desc;
		mIcons = icons;
		mCity = city;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPageNumber = getArguments().getInt(KEY_PAGE);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootView; 
		if(mPageNumber == 0){
			rootView = (ViewGroup) inflater.inflate(R.layout.main_screen, null);
			
			TextView todayDesc = (TextView) rootView.findViewById(R.id.descTodayTextView);
			TextView tomorrowDesc = (TextView) rootView.findViewById(R.id.descTomorrowTextView);
			TextView dayAfterDesc = (TextView) rootView.findViewById(R.id.DescDayAfterTextView);
			
			TextView city = (TextView) rootView.findViewById(R.id.cityTextView);
			TextView temp = (TextView) rootView.findViewById(R.id.tempTodayTextView);
			
			ImageView todayIcon = (ImageView) rootView.findViewById(R.id.todayIcon);
			ImageView tomorrowIcon = (ImageView) rootView.findViewById(R.id.TomorrowIcon);
			ImageView dayAfterIcon = (ImageView) rootView.findViewById(R.id.dayAfterIcon);
			
			
			todayDesc.setText(mDesc[0]);
			tomorrowDesc.setText(mDesc[1]);
			dayAfterDesc.setText(mDesc[2]);
			
			city.setText(mCity);
			temp.setText(mTemp[0].substring(0, 2)+"ºC");
			
			
			String preIconUrl =  "http://openweathermap.org/img/w/";
			String postIconUrl = ".png";
			String iconTodayUrl = preIconUrl+mIcons[0]+postIconUrl;
			String iconTomorrowUrl = preIconUrl+mIcons[1]+postIconUrl;
			String iconDayAfterUrl = preIconUrl+mIcons[2]+postIconUrl;
			
			
			Log.i("today icon", iconTodayUrl);
			Picasso.with(mContext).load(iconTodayUrl).resize(50, 50).centerCrop().into(todayIcon);
			Picasso.with(mContext).load(iconTomorrowUrl).resize(50, 50).centerCrop().into(tomorrowIcon);
			Picasso.with(mContext).load(iconDayAfterUrl).resize(50, 50).centerCrop().into(dayAfterIcon);
			Log.i("manu-pager-debug", "chk");
			
		}else{
			rootView = (ViewGroup) inflater.inflate(R.layout.settings_screen, null);
			Button celciusButton = (Button) rootView.findViewById(R.id.buttonCelciusSet);
			Button farenheitButton = (Button) rootView.findViewById(R.id.buttonFarenheitSet);
		}
		
		
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	private class CropSquareTransformation implements Transformation {
		@Override public Bitmap transform(Bitmap source) {
			int size = Math.min(source.getWidth(), source.getHeight());
			int x = (source.getWidth() - size) / 2;
			int y = (source.getHeight() - size) / 2;
			Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
			if (result != source) {
				source.recycle();
			}
			result = getRoundedCornerBitmap(result);
			return result;
		}

		@Override public String key() { return "square()"; }
	}
	
	
		
	
	//------------------------------------------------------------------

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth()/10;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
	
	
}
