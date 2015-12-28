package com.rentmitra;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class Singleton {
	
	
	 private static volatile Matrix sScaleMatrix;
	
	
	public static void hideSoftKeyboard(View view) {
		if (null != view) {
			InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
	public static void googleAnayticsScreen(RentMitraApplication appContorller, String screenName) {
		
		Log.i("Analytics", "Setting screen name: " + screenName);
		Tracker mTracker = appContorller.getDefaultTracker();
		mTracker.setScreenName("Image~" + screenName);
		mTracker.send(new HitBuilders.ScreenViewBuilder().build());

	}
	
	
	public static void callClient(Context contex, String mClientPhone) {
		if (mClientPhone != null && !mClientPhone.equals("") && ((TelephonyManager)contex.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number() != null) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ mClientPhone));
			contex.startActivity(intent);
		}
	}
	
}
