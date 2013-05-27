package com.touchmenotapps.realto.fragments;

import com.touchmenotapps.realto.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgentLoginFragment extends Fragment {

	private View mViewHolder;
	private Button mLoginButton;
	private EditText mUsername, mPassword;
	private OnLoginPressedListener mCallback;
	
	public interface OnLoginPressedListener {
		public void onLoginSuccess();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_agent_login, null);
		mUsername = (EditText) mViewHolder.findViewById(R.id.agent_login_username);
		mPassword = (EditText) mViewHolder.findViewById(R.id.agent_login_password);
		mLoginButton = (Button) mViewHolder.findViewById(R.id.agent_login_procced_btn);
		mLoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mUsername.getText().toString().trim().length() > 0 &&
						mPassword.getText().toString().trim().length() > 0) {
					mCallback.onLoginSuccess();
				} else 
					Toast.makeText(getActivity(), R.string.error_login_field_empty, Toast.LENGTH_LONG).show();
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
