package com.example.realestateapp.fragments;

import com.example.realestateapp.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AgentLoginFragment extends Fragment {

	private View mViewHolder;
	private Button mLoginButton;
	private OnLoginPressedListener mCallback;
	
	public interface OnLoginPressedListener {
		public void onLoginSuccess();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_agent_login, null);
		mLoginButton = (Button) mViewHolder.findViewById(R.id.agent_login_procced_btn);
		mLoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mCallback.onLoginSuccess();
			}
		});
		return mViewHolder;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	    try {
	    	mCallback = (OnLoginPressedListener) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
	    }
	}
}
