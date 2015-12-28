package com.server;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class GlobalVariables {
	
	public static final int NAVIGATION_ACTIVITY		= 100;
	public static final int SEARCH_ACTIVITY			= 101;
	public static final int PROPERTY_FRAGMENT		= 1;
	public static final int MAINTENANCE_FRAGMENT	= 2;
	public static final int PAYMENT			 		= 3;
	public static final int INVITES	 				= 4;
	public static final int EDIT_PROFILE	 		= 5;

	
	
	
	/*//Set code for day wise birthdays
	public static final int TODAYS_BIRTHDAY 		= 1001;
	public static final int TOMORROWS_BIRTHDAY 		= 1002;
	public static final int SEARCH_BIRTHDAY 		= 1003;
	public static final int SEARCH_ON_GO_BIRTHDAY 	= 1004;
	public static final int TOMORROWS_ALL_BIRTHDAYS = 5002;
	public static final int TODAYS_ALL_BIRTHDAYS 	= 5001;*/
	
	
	
	public static final float GRID_INTERVAL_ANIMATION = 0.5f;
	
	public static int getDeviceSize(Context mContext) {
		Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		Log.v("", "width:: " + width + " height::" + height);
		// Toast.makeText(getApplicationContext(), width + " :: " + height,1).show();
		int distanceHorizental = width / 3;
		return distanceHorizental;
	}
	


/*	public static String removeDeAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}*/


}
