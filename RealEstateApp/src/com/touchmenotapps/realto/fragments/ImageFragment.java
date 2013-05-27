package com.touchmenotapps.realto.fragments;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.utils.NetworkUtil;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

	public static final String ARG_IMAGE_URL = "imageURL";
	private View mViewHolder;
	private ImageView mImage;
	private NetworkUtil mNetworkUtil;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mNetworkUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_pager_image, null);
		mImage = (ImageView) mViewHolder.findViewById(R.id.pager_image);
		if(mNetworkUtil.isNetworkAvailable(getActivity()))
			new LoadImageFromWebOperations().execute(getArguments().getString(ARG_IMAGE_URL));
		else
			mImage.setBackgroundResource(R.drawable.ic_broken_file);
		return mViewHolder;
	}
	
	class LoadImageFromWebOperations extends AsyncTask<String, Void, Drawable> {
        protected Drawable doInBackground(String... urls) {
            try {
    	        InputStream is = (InputStream) new URL(urls[0]).getContent();
    	        Drawable d = Drawable.createFromStream(is, "image");
    	        return d;
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        return null;
    	    }
        }

        @SuppressWarnings("deprecation")
		protected void onPostExecute(Drawable drawable) {
        	if(drawable != null) 
        		mImage.setBackgroundDrawable(drawable);
        	else
        		mImage.setBackgroundResource(R.drawable.ic_broken_file);
        }
     }
}
