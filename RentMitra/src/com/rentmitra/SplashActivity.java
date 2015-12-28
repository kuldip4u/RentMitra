package com.rentmitra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rentmitra.login.LoginFrActivity;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT =1000; //3000;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					//startActivity(new Intent(SplashActivity.this, HomeActivity.class));
					startActivity(new Intent(SplashActivity.this, LoginFrActivity.class));
					finish();
				} 
			}, SPLASH_TIME_OUT);
		}	
}
