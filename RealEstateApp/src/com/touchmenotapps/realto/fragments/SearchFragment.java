package com.touchmenotapps.realto.fragments;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.interfaces.OnSearchListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

@SuppressLint("DefaultLocale")
public class SearchFragment extends Fragment{
	
	private View mViewHolder;
	private LinearLayout mRoomsList;
	private EditText mLocation;
	private Spinner mBedrooms, mBathrooms;
	private ArrayList<CheckBox> mRoomButtons = new ArrayList<CheckBox>();
	private OnSearchListener mCallback;
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_search, null);
		mRoomsList = (LinearLayout) mViewHolder.findViewById(R.id.search_other_rooms_list);
		mLocation = (EditText) mViewHolder.findViewById(R.id.search_location_edittext);
		mBedrooms = (Spinner) mViewHolder.findViewById(R.id.search_bedrooms);
		mBathrooms = (Spinner) mViewHolder.findViewById(R.id.search_bathrooms);
		setOtherRoomOptions();
		
		mViewHolder.findViewById(R.id.search_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearAll();
				mCallback.onSearchClicked(formQuery());
			}
		});
		
		mViewHolder.findViewById(R.id.search_show_all_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clearAll();
				mCallback.onSearchAllClicked();
			}
		});
		
		return mViewHolder;
	}
	
	private void clearAll() {
		mLocation.getEditableText().clear();
		mBedrooms.setSelection(0);
		mBathrooms.setSelection(0);
		for(CheckBox button : mRoomButtons) 
			button.setChecked(false);
	}
	
	private void setOtherRoomOptions() {
		String[] data = getResources().getStringArray(R.array.other_rooms);
		for(int i = 0; i < data.length; i++) {
			CheckBox option = new CheckBox(getActivity());
			option.setText(data[i]);
			option.setPadding(0, 10, 0, 10);
			option.setTextColor(getResources().getColor(R.color.body_text_color));
			mRoomsList.addView(option);
			mRoomButtons.add(option);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnSearchListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}
	
	private ArrayList<NameValuePair> formQuery() {
		ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
		/** Add the list of items to be posted **/
		data.add(new BasicNameValuePair("bedroom", mBedrooms.getSelectedItem().toString()));
		data.add(new BasicNameValuePair("bathroom", mBathrooms.getSelectedItem().toString()));
		data.add(new BasicNameValuePair("location", mLocation.getText().toString()));
		data.add(new BasicNameValuePair("kitchen", mRoomButtons.get(0).isChecked() ? "1" : "0"));
		data.add(new BasicNameValuePair("living-room", mRoomButtons.get(1).isChecked() ? "1" : "0"));
		data.add(new BasicNameValuePair("covered-car-garage", mRoomButtons.get(2).isChecked() ? "1" : "0"));
		data.add(new BasicNameValuePair("swimming-pool", mRoomButtons.get(3).isChecked() ? "1" : "0"));
		data.add(new BasicNameValuePair("attic", mRoomButtons.get(5).isChecked() ? "1" : "0"));
		data.add(new BasicNameValuePair("basement", mRoomButtons.get(4).isChecked() ? "1" : "0"));
		return data;
	}
}
