package com.rentmitra;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * Receive a push message from the Google cloud messaging (GCM) service. This
 * class should be modified to include functionality specific to your
 * application. This class must have a no-arg constructor and pass the sender id
 * to the superclass constructor.
 * 
 * @author ramanands
 */

public class GCMIntentService extends GCMBaseIntentService {
	private static final String LOG_TAG = "GCMIntentService";
	private static int sNotificationId = 101;
	public static final String PROJECT_ID = "760532813336"; 
	private static GCMRegisterListener sGCMRegisterListener;

	/**
	 * Register the device for GCM.
	 */
	public static void register(Context context) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		GCMRegistrar.register(context, PROJECT_ID);
	}

	public GCMIntentService() {
		super(PROJECT_ID);
	}

	public static void addGCMRegisterListener(GCMRegisterListener listener) {
		GCMIntentService.sGCMRegisterListener = listener;
	}

	/**
	 * Called on registration error. This is called in the context of a Service
	 * - no dialog or UI.
	 */
	
	@Override
	public void onError(Context context, String errorId) {
		// Log.i(LOG_TAG, "onError error id:" + errorId);
		if (null != sGCMRegisterListener) {
			sGCMRegisterListener.onErrored();
		}
	}

	/**
	 * Called when a cloud message has been received.
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		for (String key : intent.getExtras().keySet()) {
			Log.d("GCMIntentService", "onMessage - key=" + key);
		}
		// Bundle[{from=760532813336, message=Sent notificaiton, collapse_key=do_not_collapse}]
		Bundle bundle = intent.getExtras();
		Log.v("Intent bundle", bundle.toString());
		
		String message = "";
		if(bundle != null){
			message = bundle.getString("message");
		}
		
	   setNotification(message/*"AcTransit New notificaiton found."*/);
	}

	/**
	 * Called when a registration token has been received.
	 */
	@Override
	protected void onRegistered(Context context, String registration) {
		// Log.i(LOG_TAG, "Registered Device Start:" + registration);
		if (null != sGCMRegisterListener) {
			sGCMRegisterListener.onRegistered(registration);
		}
	}

	/**
	 * Called when the device has been unregistered.
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
	}

	/**
	 * Show notification in notification bar
	 * 
	 * @param message
	 *            message to be shon in notification bar
	 */
	private void setNotification(String message) {
	
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(message))
				.setContentTitle(getResources().getString(R.string.app_name))
				.setContentText(message);
		mBuilder.setAutoCancel(true);

		/*PendingIntent resultPendingIntent = PendingIntent.getActivity(this, sNotificationId, intent, Intent. FLAG_ACTIVITY_NEW_TASK);
		mBuilder.setContentIntent(resultPendingIntent);*/
		// /Vibration
		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 }); // VIBRATE
		mBuilder.setLights(Color.RED, 3000, 3000);// LED
		mBuilder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));// Ton

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(sNotificationId, mBuilder.build());
	
	}

	/**
	 * Interface to listen for registration process with the C2DM server
	 * 
	 * @author ramanands
	 * 
	 */
	public interface GCMRegisterListener {

		/**
		 * Registered successfully
		 * 
		 * @param deviceId
		 *            the device id return by the C2DM server on successful
		 *            registration
		 */
		void onRegistered(String deviceId);

		/**
		 * Error during registration process, may be re-init the process
		 */
		void onErrored();
	}
}