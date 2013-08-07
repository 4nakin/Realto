package com.touchmenotapps.realto;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import com.touchmenotapps.realto.fragments.DataListFragment;
import com.touchmenotapps.realto.fragments.PropertyDetailsFragment;
import com.touchmenotapps.realto.interfaces.OnPropertySelectedListener;
import com.touchmenotapps.realto.interfaces.OnSearchListener;
import com.touchmenotapps.realto.model.PropertyDetailsObject;
import com.touchmenotapps.realto.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements OnPropertySelectedListener, OnSearchListener {
	
	private DataListFragment mDataListFragment;
	private SlidingMenu mSearchMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_app_bar_bg));
		if(findViewById(R.id.main_fragment_container) == null) 
			getActionBar().setDisplayShowTitleEnabled(false);
		mDataListFragment = new DataListFragment();
		// configure the SlidingMenu
		mSearchMenu = new SlidingMenu(this);
		mSearchMenu.setMode(SlidingMenu.LEFT);
		mSearchMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSearchMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSearchMenu.setFadeDegree(0.35f);
		mSearchMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		mSearchMenu.setMenu(R.layout.layout_search_container);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(findViewById(R.id.main_fragment_container) != null)
			getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mDataListFragment).commit();
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_agent_login:
			startActivity(new Intent(this, AgentActivity.class));
			break;
		case R.id.menu_search_property:
			mSearchMenu.toggle();
			break;
		}
		return true;
	}
	
	@Override
	public void onPropertyListClicked(PropertyDetailsObject mData) {
		//For phones
		if(findViewById(R.id.main_fragment_container) != null) {
			Intent mDetailsActivity = new Intent(this, DetailsActivity.class);
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_ID, mData.getPropertyID());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_TITLE, mData.getPropertyTitle());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_ADDRESS, mData.getPropertyAddress());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_DESCRIPTION, mData.getPropertyDescription());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_PRICE, mData.getPropertyPrice());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_CURRENCY, mData.getCurrency());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_CONTACT, mData.getPropertyUploaderMail());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_IMAGES, mData.getPropertyImagesURL());
			mDetailsActivity.putExtra(PropertyDetailsFragment.TAG_ROOMS, mData.getPropertyRoomCount());
			startActivity(mDetailsActivity);
		} else {
			//For Tablets
			Bundle arguments = new Bundle();
			arguments.putString(PropertyDetailsFragment.TAG_ID, mData.getPropertyID());
			arguments.putString(PropertyDetailsFragment.TAG_TITLE, mData.getPropertyTitle());
			arguments.putString(PropertyDetailsFragment.TAG_ADDRESS, mData.getPropertyAddress());
			arguments.putString(PropertyDetailsFragment.TAG_DESCRIPTION, mData.getPropertyDescription());
			arguments.putString(PropertyDetailsFragment.TAG_PRICE, mData.getPropertyPrice());
			arguments.putString(PropertyDetailsFragment.TAG_CURRENCY, mData.getCurrency());
			arguments.putString(PropertyDetailsFragment.TAG_CONTACT, mData.getPropertyUploaderMail());
			arguments.putStringArray(PropertyDetailsFragment.TAG_IMAGES, mData.getPropertyImagesURL());
			arguments.putStringArray(PropertyDetailsFragment.TAG_ROOMS, mData.getPropertyRoomCount());
			PropertyDetailsFragment mFragment = new PropertyDetailsFragment();
			mFragment.setArguments(arguments);
			getSupportFragmentManager()
	            .beginTransaction()
	            .replace(R.id.property_details_fragment_container, mFragment)
	            .commit();
		}
	}

	@Override
	public void onSearchClicked(ArrayList<NameValuePair> data) {
		mSearchMenu.toggle();
		mDataListFragment.getSearchResults(data);
	}

	@Override
	public void onSearchAllClicked() {
		mSearchMenu.toggle();
		mDataListFragment.getSearchResults(null);
	}
}
