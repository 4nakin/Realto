package com.touchmenotapps.realto.utils;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.interfaces.OnImageDownloadComplete;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class LoadImageFromWebTask extends AsyncTask<String, Void, Drawable> {

	private OnImageDownloadComplete mCallback;
	private Context mContext;
	private NetworkUtil mNetworkUtil;
	
	public LoadImageFromWebTask(Context context, OnImageDownloadComplete callback) {
		mCallback = callback;
		mContext = context;
		mNetworkUtil = new NetworkUtil();
	}
	
	@Override
	protected Drawable doInBackground(String... urls) {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
		        InputStream is = (InputStream) new URL(urls[0]).getContent();
		        Drawable d = Drawable.createFromStream(is, "image");
		        return d;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return mContext.getResources().getDrawable(R.drawable.logo);
		    }
		} else 
			return mContext.getResources().getDrawable(R.drawable.logo);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		mCallback.onImageDownloadCompleted(result);
	}

}
