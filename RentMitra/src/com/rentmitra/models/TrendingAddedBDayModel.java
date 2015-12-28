package com.rentmitra.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.server.Config;

public class TrendingAddedBDayModel implements Parcelable {



	public String name;
	public String deltarank;
	public String rank;
	public String mid;


	public TrendingAddedBDayModel(JSONObject jsonObject) {
		name = jsonObject.optString("name");
		deltarank = jsonObject.optString("deltarank");
		rank = jsonObject.optString("rank");
		mid = jsonObject.optString("mid");

	}

	public static List<TrendingAddedBDayModel> getProfessionalListModel(JSONObject jsonObject) {
		List<TrendingAddedBDayModel> mBirthList = new ArrayList<TrendingAddedBDayModel>();
		JSONArray jsonArr;
		try {
			jsonObject = jsonObject.getJSONObject("TrendingFamousBirthday");
			totalPage = jsonObject.optInt("total_page");

			jsonArr = jsonObject.getJSONArray("data");
			if (jsonArr != null) {
				for (int i = 0; i < jsonArr.length(); i++) {
					mBirthList.add(new TrendingAddedBDayModel(jsonArr.getJSONObject(i)));
				}
			}
			if (Config.DEBUG) {
				Log.v("", "mBirthList.size():: " + mBirthList.size());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mBirthList;

	}

	public static int totalPage;

	public static int getAlltodayBirthRecordCount() {
		if (Config.DEBUG) {
			Log.v("", "total_page: " + totalPage);
		}
		return totalPage;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mid);
		dest.writeString(name);
		dest.writeString(deltarank);
		dest.writeString(rank);

	}

	public static final Creator<TrendingAddedBDayModel> CREATOR = new Creator<TrendingAddedBDayModel>() {

		@Override
		public TrendingAddedBDayModel createFromParcel(Parcel source) {
			return new TrendingAddedBDayModel(source);
		}

		@Override
		public TrendingAddedBDayModel[] newArray(int size) {
			return new TrendingAddedBDayModel[size];
		}
	};

	public TrendingAddedBDayModel(Parcel in) {
		mid = in.readString();
		name = in.readString();
		deltarank = in.readString();
		rank = in.readString();
	}

}
