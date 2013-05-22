package com.example.realestateapp.fragments;

import java.util.ArrayList;

import com.example.realestateapp.adapter.ItemListAdapter;
import com.example.realestateapp.model.PropertyDetailsObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class DataListFragment extends ListFragment {

	private ArrayList<PropertyDetailsObject> mData = new ArrayList<PropertyDetailsObject>();
	private ItemListAdapter mAdapter;
	private OnPropertySelectedListener mCallback;
	
	public interface OnPropertySelectedListener {
		public void onPropertyListClicked(PropertyDetailsObject mData);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new ItemListAdapter(getActivity());
		mData.add(new PropertyDetailsObject());
		mData.add(new PropertyDetailsObject());
		mData.add(new PropertyDetailsObject());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mAdapter.setListData(mData);
		setListAdapter(mAdapter);
		getListView().setPadding(10, 5, 10, 5);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	    try {
	    	mCallback = (OnPropertySelectedListener) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
	    }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onPropertyListClicked(mAdapter.getItem(position));
	}
}
