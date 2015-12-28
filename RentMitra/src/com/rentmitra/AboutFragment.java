package com.rentmitra;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rentmitra.requests.AboutRequest;
import com.server.BaseRequest;
import com.utils.DialogUtil;

public class AboutFragment extends BaseFragment {

	private static AboutFragment mFragment;
	
	private TextView mAboutTv;
	private LinearLayout mEmailLl;
	
	public static BaseFragment getInstance(Context context) {
		if (null == mFragment) {
			mFragment = new AboutFragment();
		}
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.splash_activity, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		/*mEmailLl = (LinearLayout)view.findViewById(R.id.email_ll);
		mAboutTv = (TextView)view.findViewById(R.id.about_tv);*/
		
		mEmailLl.setVisibility(View.INVISIBLE);
		getAboutService();

		Singleton.googleAnayticsScreen((RentMitraApplication) getActivity().getApplication(), getResources().getString(R.string.app_name));
	}

		/********* API Implementation *************/

		private void getAboutService() {
			
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

		}
}
