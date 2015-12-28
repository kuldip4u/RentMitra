package com.server;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;


public abstract class BaseRequest {
	private String mMessage = Config.DEFAULT_SERVICE_ERROR;
	public String mResponseCode = "0";
	private JSONObject mRespJSONObject = null;

	public boolean parse(String request, String json) {
		if (!TextUtils.isEmpty(json)) {
			try {
				mRespJSONObject = new JSONObject(json);
				if (null != mRespJSONObject) {
					mResponseCode = mRespJSONObject.optString("ResponseCode", "Response code not found");
					mMessage = mRespJSONObject.optString("Message", "Message not found");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 
	 * @return Received data object
	 */
	protected JSONObject getDataObject() {
		if (null == mRespJSONObject) {
			return null;
		}
		//return mRespJSONObject.optJSONObject("Data");
		return mRespJSONObject;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	protected JSONObject getObject(String name) {
		if (null == mRespJSONObject || TextUtils.isEmpty(name)) {
			return null;
		}

		return mRespJSONObject.optJSONObject(name);
	}

	protected int getTotalRecord() {
		if (null == getDataObject()) {
			return 0;
		}
		return getDataObject().optInt("total_page", getDataObject().optInt("total_page", 0));
	}

	/**
	 * 
	 * @return Received data array
	 */
	protected JSONArray getDataArray() {
		if (null == mRespJSONObject) {
			return null;
		}

		return mRespJSONObject.optJSONArray("Data");
	}

	/**
	 * 
	 * @return Received data array
	 */
	protected JSONArray getArray(String name) {
		if (null == mRespJSONObject || TextUtils.isEmpty(name)) {
			return null;
		}

		return mRespJSONObject.optJSONArray(name);
	}

	/**
	 * 
	 * @return Status code
	 */
	public boolean isSuccess() {
	
//		return ("200".equalsIgnoreCase(mResponseCode) || "500".equalsIgnoreCase(mResponseCode));
		
		return true;
	}

	/**
	 * 
	 * @return Response code
	 */
	public String getResponseCode() {
		return this.mResponseCode;
	}

	/**
	 * 
	 * @return Parse message. It may be error or success message
	 */
	public String getMessage() {
		return this.mMessage;
	}

	protected int mMsgResId = 0;
	protected boolean mActivityLive = true;
	protected RequestListener mRequestListener;

	public interface RequestListener {
		void onComplete(boolean success, Object data, int totalRecords);
	}

	public interface GetREsponseCodeListener
	{
		void responseCode(int responseCode);
	}
	public void setRequestListener(RequestListener listener) {
		this.mRequestListener = listener;
	}

	/**
	 * Method for parsing inner data. Outer data will be parsed by local
	 * BaseRequest.parse(String request, String json) method
	 * 
	 * @param json
	 * @return
	 */
	protected abstract boolean parse(String json);

	/**
	 * Sets custom loader message
	 * 
	 * @param msgResId
	 */
	public void setLoadingMsg(int msgResId) {
		this.mMsgResId = msgResId;
	}

	/**
	 * Set the current activity status. onStart() set true and onStop() set
	 * false;
	 * 
	 * @param live
	 */
	public abstract void setActivityStatus(boolean live);

	/**
	 * Login Session key required by all web services
	 */
	private static String sLoginSessionKey;

	/**
	 * Set the Key
	 * 
	 * @param key
	 */
	public static void setLoginSessionKey(String key) {
		sLoginSessionKey = key;
	}

	/**
	 * Gets the login session key
	 * 
	 * @return
	 */
	public static String getLoginSessionKey() {
		return sLoginSessionKey;
	}
}
