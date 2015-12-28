package com.rentmitra.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
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

public class ForgetPasswordFr extends Fragment implements OnClickListener{

	private EditText mEmailEt;
	private Button mSendBtn;
	private ImageView mBackIv;
	
	public Context mContext;
	private String mEmail;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view 	= inflater.inflate(R.layout.forget_pass_fr, null);	
		Bundle args = getArguments();
		mContext 	= getActivity();
		return view;
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mEmailEt = (EditText)view.findViewById(R.id.email_et);
		mSendBtn = (Button)view.findViewById(R.id.sendBtn);
		mBackIv	 = (ImageView)view.findViewById(R.id.backIv);
		
		mSendBtn.setOnClickListener(this);
		mBackIv.setOnClickListener(this);
		mBackIv.setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.sendBtn:
			//Toast.makeText(mContext, "Send btn", Toast.LENGTH_SHORT).show();
			forgetService();
			break;
		
		case R.id.backIv:
			((LoginFrActivity)mContext).onBackPressed();
			break;
		}
	}
	
	
	private void forgetService() {
		
		mEmail = mEmailEt.getText().toString().trim();
		
		if(mEmail==null || mEmail.equalsIgnoreCase("") ){
			mEmailEt.setError("Email Required");
			return;
		}else if(!Validation.isValidEmail(mEmail)){
			mEmailEt.setError("Invalid Email");
			return;
		}else
			mEmailEt.setError(null);
	
	/*	
		APIService service = APIService.forgetPassRequest(mContext, mEmail);
		service.getData(true);
		
		service.setServiceListener(new APIServiceListener() {
			
			@Override
			public void onCompletion(APIService service, Object data, Error error) {
				
				//{"data": {"message": "email is required","responseCode": 201}}
			
				if (error == null && data != null) {
					JSONObject json = (JSONObject) data;
					Log.v("JSONObject", json.toString());
					
					try {
						if("200".equalsIgnoreCase(json.getString("responseCode"))){
							Toast.makeText(mContext, json.getString("message"), Toast.LENGTH_SHORT).show();
							//getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_login_rl, signUpFragment).addToBackStack(null).commit();
							((LoginFrActivity)mContext).onBackPressed();
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
