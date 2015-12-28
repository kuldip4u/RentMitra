package com.rentmitra.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rentmitra.R;
import com.utils.DialogUtil;
import com.utils.Validation;

public class SignUpFragment extends Fragment implements OnClickListener{
	
	private ImageView mBackIv;
	private Button mSignupBtn;
	private EditText mUserIdEt, mPasswordEt;
	
	private String mUserStr, mPassStr;
	Context mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
			View view 	= inflater.inflate(R.layout.signup_fragment, null);	
			Bundle args = getArguments();
			mContext 	= getActivity();
			return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mUserIdEt 	= (EditText)view.findViewById(R.id.userId_et);
		mPasswordEt = (EditText)view.findViewById(R.id.password_et);
		mSignupBtn 	= (Button)view.findViewById(R.id.signup_btn);
		mBackIv	 = (ImageView)view.findViewById(R.id.backIv);
		
		mBackIv.setOnClickListener(this);
		mSignupBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signup_btn:
			//Toast.makeText(mContext, "signup_btn Pressed", Toast.LENGTH_SHORT).show();
			signUpService();
			break;

		case R.id.backIv:
			((LoginFrActivity)mContext).onBackPressed();
			break;
			
		}
	}
	
	private void signUpService() {
		
		mUserStr = mUserIdEt.getText().toString().trim();
		mPassStr = mPasswordEt.getText().toString().trim();
		
		if(mUserStr==null || mUserStr.equalsIgnoreCase("") ){
			mUserIdEt.setError("Username Required");
			return;
		}else if(!Validation.isValidEmail(mUserStr)){
			mUserIdEt.setError("Invalid Email");
			return;
		}else
			mUserIdEt.setError(null);
		
		if(mPassStr==null || mPassStr.equalsIgnoreCase("")){
			mPasswordEt.setError("Password Required");
			return;
		}else
			mUserIdEt.setError(null);
			
		
	/*	APIService service = APIService.signUpRequest(mContext, LoginFrActivity.mGCMRegId, mUserStr, mPassStr);
		service.getData(true);
		
		service.setServiceListener(new APIServiceListener() {
			
			@Override
			public void onCompletion(APIService service, Object data, Error error) {
				
			
				if (error == null && data != null) {
					JSONObject json = (JSONObject) data;
					Log.v("JSONObject", json.toString());
					
					try {
						if("200".equalsIgnoreCase(json.getString("responseCode"))){
							Toast.makeText(mContext, json.getString("message"), Toast.LENGTH_SHORT).show();
							PrefUtil.savePreferences(mContext, PrefUtil.tipPref, mUserStr);
							PrefUtil.savePreferences(mContext, PrefUtil.password, mPassStr);
							Intent intent = new Intent(mContext, CategoryFrActivity.class);
							startActivity(intent);
							((LoginFrActivity)mContext).finish();
						}else{
							DialogUtil.showOkListenerDialog(mContext, "Tip Now", json.getString("message"), false, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Dialog dialog = (Dialog) v.getTag();
									dialog.dismiss();
								}
							});
						}
					} catch (JSONException e) {		e.printStackTrace();		}
				}
			}
		});*/
	}
}
