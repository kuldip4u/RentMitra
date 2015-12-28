package com.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rentmitra.R;


public class DialogUtil {


	public static Dialog showOkCancelDialog(final Context context, String titleResId, String msgResId, boolean isYes, OnClickListener okListener ) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

	
		
		dialog.setContentView(R.layout.ok_cancel_dialog);
		Button okBtn = (Button) dialog.findViewById(R.id.ok_btn);
		Button canbtn= (Button) dialog.findViewById(R.id.cancel_btn);
		
		if (isYes) {
			okBtn.setText("YES");
			canbtn.setText("NO");
		}
		
		TextView titleTv = (TextView) dialog.findViewById(R.id.title_tv);
		if (titleResId.length() > 0) {
			titleTv.setText(titleResId);
		} else {
			titleTv.setVisibility(View.GONE);
		}
		TextView messageTv = (TextView) dialog.findViewById(R.id.message_tv);
		messageTv.setText(msgResId);

		canbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		
	
		/*Button cancelbtn = (Button) dialog.findViewById(R.id.cancel_btn);
		cancelbtn.setTag(dialog);
		cancelbtn.setOnClickListener(cancelListener);*/

		
		okBtn.setTag(dialog);
		okBtn.setOnClickListener(okListener);

		dialog.show();
		return dialog;
	}
	
	
	public static Dialog createProgressDialogDefaultWithMessage(Context context, int msgResId) {
		ProgressDialog dialog = new ProgressDialog(context);
		if (msgResId != 0) {
			dialog.setMessage(context.getResources().getString(msgResId));
		}
		dialog.setCanceledOnTouchOutside(false);

		return dialog;
	}
	
	public static void showOkDialog(Context context, String msg, String titleRes) {
		if (null == context || null == context || null == msg) {
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		if (!titleRes.equals("")) {
			builder.setTitle(titleRes);
		}

		builder.setMessage(msg);
		builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});
		builder.setCancelable(true);

		builder.show();
	}
	
	/*public static Dialog showImageDialog( Context context, String imgUri) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		dialog.setContentView(R.layout.show_photo);
		
		ImageView canbtn= (ImageView) dialog.findViewById(R.id.closeIv);
	
		TouchImageView imageIv = (TouchImageView) dialog.findViewById(R.id.img_pager);
		//imageIv.setImageBitmap(BitmapFactory.decodeFile(imgUri));
		imageIv.setImageURI(Uri.parse(imgUri));

		//imageIv.setImageURI(Uri.parse(imgUri));
		canbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		dialog.show();
		return dialog;
	}*/
	

	
	
}
