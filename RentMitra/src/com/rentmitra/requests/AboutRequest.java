package com.rentmitra.requests;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.rentmitra.R;
import com.rentmitra.models.TrendingAddedBDayModel;
import com.server.BaseRequest;
import com.server.Config;
import com.server.HttpConnector;
import com.server.HttpConnector.HttpResponseListener;
import com.server.HttpConnector.UrlType;
import com.utils.DialogUtil;

public class AboutRequest extends BaseRequest implements HttpResponseListener {

	public static final String TAG = AboutRequest.class.getSimpleName();
	private static final int REQ_CODE_GET_ABOUT_TEXT = 1;

	private boolean mRequesting;
	private HttpConnector mHttpConnector;
	private Context mContext;
	private List<TrendingAddedBDayModel> mGetProfessionList;
	JSONObject jsonObjectData;
	private String urlName = "";
	private int UrlCode;
	private String url = "";

	public AboutRequest(Context mContext) {
		this.mContext = mContext;
		mRequesting = false;
		mHttpConnector = new HttpConnector(mContext);
		mHttpConnector.setHttpResponseListener(this);
	}

	public void getAboutRequest() {
		this.UrlCode = UrlCode;
		if (mRequesting || !mActivityLive) {
			if (Config.DEBUG) {
				Log.v(TAG, "Already getting media list...");
			}
			return;
		}

		mRequesting = true;
		url = "about";
		mHttpConnector.executeAsync(url, REQ_CODE_GET_ABOUT_TEXT, "get", false, "", null, UrlType.SERVICE);

	}

	public void setLoader(View view) {
		mHttpConnector.setLoader(view);
	}

	private ProgressBar mProgressBar;

	public void setProgressBar(ProgressBar progressBar) {
		this.mProgressBar = progressBar;
	}

	public void SetDialog() {
		mHttpConnector.setDialog(DialogUtil.createProgressDialogDefaultWithMessage(mContext, R.string.please_wait));
	}

	@Override
	public void onResponse(int reqCode, int statusCode, String json) {
		switch (reqCode) {
		case REQ_CODE_GET_ABOUT_TEXT:
			mRequesting = false;
			if (parse(json)) {
				if (null != mRequestListener) {
					mRequestListener.onComplete(true, getDataObject(), 0);
				} else {
					// Handling Error Messages
					if (Config.DEBUG) {
						Log.e(TAG, "Failed to get Profession list. Errors : " + getMessage());
					}
					if (null != mRequestListener) {
						mRequestListener.onComplete(false, getMessage(), 0);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCancel(boolean canceled) {
	}

	@Override
	public void onProgressChange(int progress) {
		// if (null != mProgressBar) {
		// mProgressBar.setProgress(progress);
		// mProgressBar.setVisibility(progress >= 0 && progress <= 99 ? View.VISIBLE: View.GONE);
		// }
	}

	@Override
	public void setActivityStatus(boolean live) {
		super.mActivityLive = live;
		mHttpConnector.setActivityStatus(mActivityLive);
	}

	@Override
	protected boolean parse(String json) {

		if (super.parse("TrendingFamousBirthday", json) && isSuccess()) {
			return true;
		}
		return false;
	}
}
