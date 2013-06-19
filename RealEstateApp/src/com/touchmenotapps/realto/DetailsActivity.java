package com.touchmenotapps.realto;

import com.touchmenotapps.realto.fragments.PropertyDetailsFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class DetailsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_details);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_app_bar_bg));
		getActionBar().setTitle(getIntent().getStringExtra(PropertyDetailsFragment.TAG_TITLE));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		/** Fetch and bundle the data */
		Bundle arguments = new Bundle();
		arguments.putString(PropertyDetailsFragment.TAG_ID, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_ID));
		arguments.putString(PropertyDetailsFragment.TAG_TITLE, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_TITLE));
		arguments.putString(PropertyDetailsFragment.TAG_ADDRESS, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_ADDRESS));
		arguments.putString(PropertyDetailsFragment.TAG_DESCRIPTION, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_DESCRIPTION));
		arguments.putString(PropertyDetailsFragment.TAG_PRICE, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_PRICE));
		arguments.putString(PropertyDetailsFragment.TAG_CURRENCY, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_CURRENCY));
		arguments.putString(PropertyDetailsFragment.TAG_CONTACT, 
				getIntent().getStringExtra(PropertyDetailsFragment.TAG_CONTACT));
		arguments.putStringArray(PropertyDetailsFragment.TAG_IMAGES, 
				getIntent().getStringArrayExtra(PropertyDetailsFragment.TAG_IMAGES));
		arguments.putStringArray(PropertyDetailsFragment.TAG_ROOMS, 
				getIntent().getStringArrayExtra(PropertyDetailsFragment.TAG_ROOMS));
		/** Start the details fragment */
		PropertyDetailsFragment mFragment = new PropertyDetailsFragment();
		mFragment.setArguments(arguments);
		getSupportFragmentManager()
	        .beginTransaction()
	        .replace(R.id.details_fragment_contatiner, mFragment)
	        .commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
