package com.rentmitra;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class RentMitraApplication extends Application{
	
	private Tracker mTracker;
	
	private static final String PROPERTY_ID = "";//"UA-71183401-1";
	
	synchronized public Tracker getDefaultTracker() {
		if (mTracker == null) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			// To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
			mTracker = GoogleAnalytics.getInstance(this).newTracker(R.xml.app_tracker);
			mTracker = analytics.newTracker(PROPERTY_ID);
		}
		return mTracker;
	}

}
