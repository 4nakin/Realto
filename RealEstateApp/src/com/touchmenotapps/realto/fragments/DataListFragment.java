package com.touchmenotapps.realto.fragments;

import java.util.ArrayList;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.adapter.ItemListAdapter;
import com.touchmenotapps.realto.interfaces.OnPropertySelectedListener;
import com.touchmenotapps.realto.model.PropertyDetailsObject;
import com.touchmenotapps.realto.utils.PropertyListLoader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

public class DataListFragment extends ListFragment implements LoaderCallbacks<ArrayList<PropertyDetailsObject>>{
	
	private final int LOADER_ID = 123;
	private ItemListAdapter mAdapter;
	private OnPropertySelectedListener mCallback;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new ItemListAdapter(getActivity());
		setEmptyText(getString(R.string.error_server_data_fetching));
		setListAdapter(mAdapter);
		setListShown(false);
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnPropertySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onPropertyListClicked(mAdapter.getItem(position));
	}

	@Override
	public Loader<ArrayList<PropertyDetailsObject>> onCreateLoader(int arg0,
			Bundle arg1) {
		return new PropertyListLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<PropertyDetailsObject>> arg0,
			ArrayList<PropertyDetailsObject> data) {
    	mAdapter.setListData(data);
    	/*if(getActivity().findViewById(R.id.property_details_fragment_container) != null) 
    		mCallback.onPropertyListClicked(mAdapter.getItem(0));*/
    	if(isResumed())
    		setListShown(true);
    	else
    		setListShownNoAnimation(true);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<PropertyDetailsObject>> arg0) {
		 mAdapter.setListData(new ArrayList<PropertyDetailsObject>());
	}
}
