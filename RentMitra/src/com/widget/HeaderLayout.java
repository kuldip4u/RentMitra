package com.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rentmitra.R;

public class HeaderLayout {

	public ImageButton mLeftImgBut;
	public ImageButton mRightImgBut;
	public TextView mMiddleTv;
	RelativeLayout mHeaderRl;
	ImageView mLogoImg;

	public HeaderLayout(View view) {
		mLeftImgBut = (ImageButton) view.findViewById(R.id.img_left);
		mRightImgBut = (ImageButton) view.findViewById(R.id.img_right);
		mMiddleTv = (TextView) view.findViewById(R.id.tv_middle);
		mHeaderRl = (RelativeLayout) view.findViewById(R.id.header_rl);
		mLogoImg = (ImageView) view.findViewById(R.id.logo_img);
	}
	
	public HeaderLayout(Context context) {
		mLeftImgBut = (ImageButton) ((Activity) context).findViewById(R.id.img_left);
		mRightImgBut = (ImageButton) ((Activity) context).findViewById(R.id.img_right);
		mMiddleTv = (TextView) ((Activity) context).findViewById(R.id.tv_middle);
		mHeaderRl = (RelativeLayout) ((Activity) context).findViewById(R.id.header_rl);
		mLogoImg = (ImageView) ((Activity) context).findViewById(R.id.logo_img);
	}

	public void setHeaderIII(int imgButLeft, int middleImg, int imgButRight) {

		if (middleImg == 0) {
		} else {
			mLogoImg.setVisibility(View.VISIBLE);
			mLogoImg.setVisibility(View.VISIBLE);
			mMiddleTv.setVisibility(View.GONE);
		}
		if (imgButLeft == 0) {
			mLeftImgBut.setVisibility(View.INVISIBLE);
		}
		if (imgButRight == 0) {
			mRightImgBut.setVisibility(View.INVISIBLE);
		}
		mLeftImgBut.setImageResource(imgButLeft);
		mRightImgBut.setImageResource(imgButRight);
	}

	public void setHeaderITI(int imgButLeft, String middleTv, int imgButRight) {
		Log.v("", "middleTv: " + middleTv);
		mLogoImg.setVisibility(View.GONE);
		
		if (middleTv == (null) || middleTv.equals("") || TextUtils.isEmpty(middleTv)) {
			mMiddleTv.setVisibility(View.INVISIBLE);
		} else {
			mMiddleTv.setVisibility(View.VISIBLE);
			mMiddleTv.setText(middleTv);
		}
		if (imgButLeft == 0) {
			mLeftImgBut.setVisibility(View.INVISIBLE);
		}
		
		mRightImgBut.setVisibility(imgButRight == 0 ? View.INVISIBLE : View.VISIBLE);
		
		mLeftImgBut.setImageResource(imgButLeft);
		mRightImgBut.setImageResource(imgButRight);
	
	}

	public void setListenerITI(OnClickListener leftImg, OnClickListener logoImg, OnClickListener rightImg) {
		mLeftImgBut.setOnClickListener(leftImg);
		mLogoImg.setOnClickListener(logoImg);
		mRightImgBut.setOnClickListener(rightImg);
	}

}
