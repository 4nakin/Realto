package com.example.realestateapp.fragments;

import com.example.realestateapp.R;
import com.example.realestateapp.adapter.ImageGalleryAdapter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GalleryDialogFragment extends DialogFragment {
	
	public static final String ARG_IMAGE_URLS = "imageURLS"; 
	private View mViewHolder;
	private TextView mGalleryCounter, mGalleryEmpty;
	private ImageGalleryAdapter mAdapter;
	private ViewPager mGalleryPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.image_gallery);
		
		mViewHolder = inflater.inflate(R.layout.dialog_gallery, null);
		mAdapter = new ImageGalleryAdapter(getChildFragmentManager());
		mGalleryPager = (ViewPager) mViewHolder.findViewById(R.id.gallery_dialog_image_pager);
		mGalleryCounter = (TextView) mViewHolder.findViewById(R.id.gallery_dialog_counter);
		mGalleryEmpty = (TextView) mViewHolder.findViewById(R.id.gallery_dialog_empty);
		
		if(getArguments().getStringArray(ARG_IMAGE_URLS) != null && getArguments().getStringArray(ARG_IMAGE_URLS).length > 0) {
			mAdapter.setURLS(getArguments().getStringArray(ARG_IMAGE_URLS));
			mGalleryCounter.setText(String.valueOf(1) + " | " + String.valueOf(getArguments().getStringArray(ARG_IMAGE_URLS).length));
			mGalleryPager.setAdapter(mAdapter);
		} else {
			mGalleryEmpty.setVisibility(View.VISIBLE);
			mGalleryPager.setVisibility(View.GONE);
			mGalleryCounter.setVisibility(View.GONE);
		}
		
		mGalleryPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int page) {
				if(getArguments().getStringArray(ARG_IMAGE_URLS) != null && getArguments().getStringArray(ARG_IMAGE_URLS).length > 0) 
					mGalleryCounter.setText(String.valueOf((page+1)) + " | " + String.valueOf(getArguments().getStringArray(ARG_IMAGE_URLS).length));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }
			
			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
		
		return mViewHolder;
	}
}
