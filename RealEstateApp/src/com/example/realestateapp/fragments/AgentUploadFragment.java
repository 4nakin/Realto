package com.example.realestateapp.fragments;

import com.example.realestateapp.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AgentUploadFragment extends Fragment {

	private View mViewHolder;
	private EditText mPropertyName, mPropertyPrice, mPropertyDescription;
	private Button mSelectLocationBtn, mAddImageBtn, mUploadBtn, mDiscardBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_agent_upload, null);
		mPropertyName = (EditText) mViewHolder.findViewById(R.id.upload_property_name);
		mPropertyPrice = (EditText) mViewHolder.findViewById(R.id.upload_enter_price);
		mPropertyDescription = (EditText) mViewHolder.findViewById(R.id.upload_description);
		mSelectLocationBtn = (Button) mViewHolder.findViewById(R.id.upload_select_place_btn);
		mAddImageBtn = (Button) mViewHolder.findViewById(R.id.upload_add_image_btn);
		mUploadBtn = (Button) mViewHolder.findViewById(R.id.upload_start_btn);
		mDiscardBtn = (Button) mViewHolder.findViewById(R.id.upload_discard_btn);

		mDiscardBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clearEntry();
			}
		});

		mAddImageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showImageMenu();
			}
		});

		mSelectLocationBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

			}
		});

		mUploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

			}
		});

		return mViewHolder;
	}

	private void clearEntry() {
		mPropertyName.getEditableText().clear();
		mPropertyPrice.getEditableText().clear();
		mPropertyDescription.getEditableText().clear();
	}
	
	private void showImageMenu() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builder.setItems(getResources().getStringArray(R.array.image_picker_options), new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
