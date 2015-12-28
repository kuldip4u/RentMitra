package com.rentmitra.login;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gcm.GCMRegistrar;
import com.rentmitra.GCMIntentService;
import com.rentmitra.GCMIntentService.GCMRegisterListener;
import com.rentmitra.R;

public class LoginFrActivity extends FragmentActivity implements GCMRegisterListener{
	
	private RelativeLayout mMainrl;
	
	private LoginFragment  loginFragment;
	private SignUpFragment signUpFragment;
	private ForgetPasswordFr forgetPasswordFr;
	
	android.support.v4.app.FragmentManager mFragmentManager;
	
	public static String mGCMRegId = "Android Simulator";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.login_activity);
	
		gcmRegister();
		
		mFragmentManager = this.getSupportFragmentManager();
		
		mMainrl = (RelativeLayout)findViewById(R.id.main_login_rl);
		
		loginFragment 	= new LoginFragment();
		signUpFragment 	= new SignUpFragment();
		forgetPasswordFr= new ForgetPasswordFr(); 
		
		mFragmentManager.beginTransaction().replace(R.id.main_login_rl, loginFragment).commit();
		
	}
	
	
	/** ........... GCM REGISTRATION...........	*/
	
	private void  gcmRegister(){
		/* GCM service variables */
		try {
			GCMIntentService.addGCMRegisterListener(this);
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			final String regId = GCMRegistrar.getRegistrationId(this);
			// Log.d("LoginActivity", "onCreate - regId=" + regId);
			if (regId.equals("")) {
				GCMRegistrar.register(this, GCMIntentService.PROJECT_ID);
			} else {
				onRegistered(regId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRegistered(String deviceId) {
		// TODO Auto-generated method stub
		mGCMRegId = deviceId;
		Log.v("mGCMRegId", "mGCMRegId : " + LoginFrActivity.mGCMRegId);
	}

	@Override
	public void onErrored() {
		// TODO Auto-generated method stub
		
	}

}
