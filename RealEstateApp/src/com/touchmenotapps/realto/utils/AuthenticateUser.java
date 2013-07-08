package com.touchmenotapps.realto.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.fragments.AgentLoginFragment.OnLoginPressedListener;

/**
 * 
 * @author Arindam Nath
 *
 */
public class AuthenticateUser extends AsyncTask<String, Void, Integer> {
	
	private final String AUTHENTICATION_URL = "";
	private final String TAG_USERNAME = "";
	private final String TAG_PASSWORD = "";
	
	private final int ERROR_NO_CONNECTION = 1;
	private final int ERROR_WRONG_INPUT = ERROR_NO_CONNECTION + 1;
	private final int ERROR_EXCEPTION = ERROR_NO_CONNECTION + 2;
	private final int ERROR_WRONG_CREDENTIALS = ERROR_NO_CONNECTION + 3;
	private final int SUCCESS = ERROR_NO_CONNECTION + 4;
	
	private ProgressDialog mProgressDialog;
	private Context mContext;
	private NetworkUtil mNetworkUtil;
	private OnLoginPressedListener mCallback;
	
	public AuthenticateUser(Context context, OnLoginPressedListener callback) {
		mContext = context;
		mNetworkUtil = new NetworkUtil();
		mCallback = callback;
		mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setMessage(context.getText(R.string.authenticating));
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}

	@Override
	protected Integer doInBackground(String... params) {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			if(params[0].trim().length() > 0 && params[1].trim().length() > 0) {
				HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(AUTHENTICATION_URL);
			    try {
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair(TAG_USERNAME, params[0].trim()));
			        nameValuePairs.add(new BasicNameValuePair(TAG_PASSWORD, params[1].trim()));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        HttpResponse response = httpclient.execute(httppost);
			        if(response.toString().equals("")) 
			        	return SUCCESS;
			        else 
			        	return ERROR_WRONG_CREDENTIALS;
			    } catch (Exception e) {
			    	e.printStackTrace();
			    	return ERROR_EXCEPTION;
			    }
			} else 
				return ERROR_WRONG_INPUT;
		} else
			return ERROR_NO_CONNECTION;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		mProgressDialog.dismiss();
		switch(result) {
		case SUCCESS:
			mCallback.onLoginSuccess();
			break;
		case ERROR_WRONG_CREDENTIALS:
			Toast.makeText(mContext, R.string.error_login_worng_credintials, Toast.LENGTH_LONG).show();
			break;
		case ERROR_EXCEPTION:
			Toast.makeText(mContext, R.string.error_login_failed, Toast.LENGTH_LONG).show();
			break;
		case ERROR_WRONG_INPUT:
			Toast.makeText(mContext, R.string.error_login_field_empty, Toast.LENGTH_LONG).show();
			break;
		case ERROR_NO_CONNECTION:
			Toast.makeText(mContext, R.string.error_no_network, Toast.LENGTH_LONG).show();
			break;
		}
	}
}
