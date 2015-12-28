package com.server;


import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rentmitra.RentMitraApplication;

public class Config {

	public static final String DEVICE_TYPE_ID = "AndroidPhone";
	public static final String REQUEST_STOPPED_MSG = "Activity is not running, quitting request";
	public static final int DEFAULT_PAGE_INDEX = 1;

	public static final int PAGE_SIZE = 10;

	public static final boolean DEBUG = false;

	public static final String ENVIRONMENT = "devel";

	public static String SERVICE_URL = "http://www.famousbirthdays.com/app/index-v4.4.php/";
	public static String UPLOAD_URL;
	public static String IMAGE_URL;

	public static String DeviceIPAddress = "";
	public static String DEVICE_ID = "";

	public static final String SOCIAL_FB = "Facebook";
	public static final String SOCIAL_GPLUS = "Google";
	public static final String WEB = "Web";

	public static final String DEFAULT_SERVICE_ERROR = "Can not connect to server!";

	/*public static void googleAnayticsScreen(RentMitraApplication appContorller, String screenName) {

		Log.i("Analytics", "Setting screen name: " + screenName);
		Tracker mTracker = appContorller.getDefaultTracker();
		mTracker.setScreenName("Image~" + screenName);
		mTracker.send(new HitBuilders.ScreenViewBuilder().build());

	}*/
}
