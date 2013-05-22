package com.example.realestateapp.fragments;

import java.io.InputStream;
import java.net.URL;

import com.example.realestateapp.R;

import android.graphics.drawable.Drawable;
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
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_pager_image, null);
		mImage = (ImageView) mViewHolder.findViewById(R.id.pager_image);
		Drawable image = loadImageFromWebOperations(getArguments().getString(ARG_IMAGE_URL));
		if(image != null) 
			mImage.setBackgroundDrawable(image);
		else 
			mImage.setBackgroundResource(R.drawable.ic_broken_file);
		return mViewHolder;
	}
	
	private Drawable loadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "image");
	        return d;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
