package com.touchmenotapps.realto.adapter;

import com.touchmenotapps.realto.fragments.ImageFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImageGalleryAdapter extends FragmentPagerAdapter  {

	private String[] mURLs;
	
	public ImageGalleryAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setURLS(String[] data) {
		mURLs = data;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new ImageFragment();
		Bundle args = new Bundle();
		args.putString(ImageFragment.ARG_IMAGE_URL, mURLs[position]);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return mURLs.length;
	}
}
