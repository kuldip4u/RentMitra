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
import android.widget.TextView;
import android.widget.Toast;

import com.rentmitra.R;
import com.rentmitra.dashboard.HomeActivity;
import com.utils.DialogUtil;
import com.utils.Validation;

public class LoginFragment extends Fragment implements OnClickListener{
	
	Context mContext;
	private Button mLoginBtn;
	private EditText mUserIdEt, mPasswordEt;
	private TextView mSignUpTv, mForgetTv;
	private String mUserStr, mPassStr;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
			View view 	= inflater.inflate(R.layout.login_fragment, null);	
			Bundle args = getArguments();
			mContext 	= getActivity();
			return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mUserIdEt 	= (EditText)view.findViewById(R.id.userId_et);
		mPasswordEt = (EditText)view.findViewById(R.id.password_et);
		mLoginBtn 	= (Button)view.findViewById(R.id.login_btn);
		mSignUpTv   = (TextView)view.findViewById(R.id.signupTv);
		mForgetTv	= (TextView)view.findViewById(R.id.forgetTv);
		
		mLoginBtn.setOnClickListener(this);
		mSignUpTv.setOnClickListener(this);
		mForgetTv.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
			
		switch (v.getId()) {
		case R.id.login_btn:
			loginService();
			//Toast.makeText(mContext, "Login Pressed", Toast.LENGTH_SHORT).show();
			break;

		case R.id.signupTv:
			//Toast.makeText(mContext, "signupTv Pressed", Toast.LENGTH_SHORT).show();
			SignUpFragment signUpFragment = new SignUpFragment();
			getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_login_rl, signUpFragment).addToBackStack(null).commit();
			mUserIdEt.setText("");
			mPasswordEt.setText("");
			break;
			
		case R.id.forgetTv:
			//Toast.makeText(mContext, "forgetTv Pressed", Toast.LENGTH_SHORT).show();
			ForgetPasswordFr forgetPasswordFr = new ForgetPasswordFr();
			getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_login_rl, forgetPasswordFr).addToBackStack(null).commit();
			mUserIdEt.setText("");
			mPasswordEt.setText("");
			break;
		}
	}
	
	private void loginService() {
		
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
		}else if(mPassStr.length() <4){
			mPasswordEt.setError("Password must have minimum 4 characters");
		}else
			mUserIdEt.setError(null);
		
		mContext.startActivity(new Intent(mContext, HomeActivity.class));
		((LoginFrActivity)mContext).finish();
			
		
	/*	APIService service = APIService.loginRequest(mContext, LoginFrActivity.mGCMRegId, mUserStr, mPassStr);
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
							PrefUtil.savePreferences(mContext, PrefUtil.userid, mUserStr);
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
		});
	*/
	}
}
