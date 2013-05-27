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
		//For phones
		if(findViewById(R.id.main_fragment_container) != null) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setDisplayShowTitleEnabled(true);
			getActionBar().setTitle(mData.getPropertyTitle());
			changeFragment(new PropertyDetailsFragment(mData));
		} else {
			getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.property_details_fragment_container, new PropertyDetailsFragment(mData))
            .commit();
		}
	}
}
