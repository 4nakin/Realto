package com.touchmenotapps.realto;

import com.touchmenotapps.realto.fragments.AgentLoginFragment;
import com.touchmenotapps.realto.fragments.AgentUploadFragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

public class AgentActivity extends Activity
	implements AgentLoginFragment.OnLoginPressedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agent);
		getActionBar().setTitle(R.string.menu_agent_login);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getFragmentManager().beginTransaction().replace(R.id.agent_fragment_container, new AgentLoginFragment()).commit();
	}
	
	/**
     * Whether or not we're showing the back of the card (otherwise showing the front).
     */    
	public void flipCard(Fragment fragment) {
		getActionBar().setTitle(R.string.new_upload);				
		getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.agent_fragment_container, fragment)
                .commit();
    }

	@Override
	public void onLoginSuccess() {
		flipCard(new AgentUploadFragment());
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}	
}
