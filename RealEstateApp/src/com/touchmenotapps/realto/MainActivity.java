package com.touchmenotapps.realto;

import com.touchmenotapps.realto.fragments.DataListFragment;
import com.touchmenotapps.realto.fragments.PropertyDetailsFragment;
import com.touchmenotapps.realto.model.PropertyDetailsObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements DataListFragment.OnPropertySelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayShowTitleEnabled(false);
		if(findViewById(R.id.main_fragment_container) != null)
			getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new DataListFragment()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
				getActionBar().setDisplayHomeAsUpEnabled(false);
				getActionBar().setDisplayShowTitleEnabled(false);
				getSupportFragmentManager().popBackStack();
			}
			break;
		case R.id.menu_agent_login:
			startActivity(new Intent(this, AgentActivity.class));
			break;
		}
		return true;
	}
	
	/**
     * Whether or not we're showing the back of the card (otherwise showing the front).
     */    
	public void changeFragment(Fragment fragment) {
		getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

	@Override
	public void onPropertyListClicked(PropertyDetailsObject mData) {
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
		//For phones
		if(findViewById(R.id.main_fragment_container) != null) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setDisplayShowTitleEnabled(true);
			getActionBar().setTitle(mData.getPropertyTitle());
			changeFragment(mFragment);
		} else {
			getSupportFragmentManager()
	            .beginTransaction()
	            .replace(R.id.property_details_fragment_container, mFragment)
	            .commit();
		}
	}
}
