package com.rentmitra.properties;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rentmitra.BaseFragment;
import com.rentmitra.R;
import com.rentmitra.RentMitraApplication;
import com.rentmitra.Singleton;

public class PropertiesInnerFr extends BaseFragment {

	private static PropertiesInnerFr mFragment;
	
	private TextView mAboutTv;
	private LinearLayout mEmailLl;
	
	public static BaseFragment getInstance(Context context) {
		if (null == mFragment) {
			mFragment = new PropertiesInnerFr();
		}
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maintenance_fragment, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		//mEmailLl.setVisibility(View.INVISIBLE);
		//getAboutService();

//		Singleton.googleAnayticsScreen((RentMitraApplication) getActivity().getApplication(), getResources().getString(R.string.app_name));
	}

		/********* API Implementation *************/

	/*	private void getAboutService() {
			
			AboutRequest mAboutRequest = new AboutRequest(getActivity());
			mAboutRequest.getAboutRequest();
			
			mAboutRequest.setRequestListener(new BaseRequest.RequestListener() {

				@Override
				public void onComplete(boolean success, Object data, int totalRecords) {
					if (success) {
						if (data != null) {
							String value="";
							try {
								value = ((JSONObject)data).getString("about");
							} catch (JSONException e) {	e.printStackTrace(); }
							mAboutTv.setText(value);
							mEmailLl.setVisibility(View.VISIBLE);
						} else {
							
							DialogUtil.showOkDialog( getActivity(), "", getResources().getString(R.string.Record_not_found));

						}
					}
				}
			});

		}*/
}
