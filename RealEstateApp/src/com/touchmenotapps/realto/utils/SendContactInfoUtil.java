package com.touchmenotapps.realto.utils;

import com.touchmenotapps.realto.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendContactInfoUtil {
	
	private Context mContext;
	private LayoutInflater mInflater;
	
	public SendContactInfoUtil(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void getContactedDialog() {
		final AlertDialog mContactDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
		mContactDialog.setTitle(R.string.contact_info);
		View mView = mInflater.inflate(R.layout.dialog_contact, null);
		final EditText mName = (EditText) mView.findViewById(R.id.contact_name);
		final EditText mMail = (EditText) mView.findViewById(R.id.contact_mail);
		final EditText mPhone = (EditText) mView.findViewById(R.id.contact_phone);
		
		mContactDialog.setView(mView);
		mContactDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mContext.getText(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mContactDialog.dismiss();
			}
		});
		mContactDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getText(R.string.send), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mName.getText().toString().trim().length() > 0 &&
						mMail.getText().toString().trim().length() > 0 && 
						mPhone.getText().toString().trim().length() > 0 ) { 
					mContactDialog.dismiss();
				} else 
					Toast.makeText(mContext, R.string.error_contact_entry, Toast.LENGTH_LONG).show();
			}
		});
		mContactDialog.show();
	}
}
