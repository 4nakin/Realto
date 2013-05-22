package com.example.realestateapp.fragments;

import com.example.realestateapp.R;
import com.example.realestateapp.model.PropertyDetailsObject;
import com.example.realestateapp.utils.SendContactInfoUtil;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class PropertyDetailsFragment extends Fragment{

	private View mViewHolder;
	private Button mInterestedButton;
	private ImageButton mImageButton;
	private TextView mTitle, mAddress, mDescription, mPrice;
	private SendContactInfoUtil mContactUtil;
	private PropertyDetailsObject mPropertyDetails;
	
	public PropertyDetailsFragment() {
		mPropertyDetails = new PropertyDetailsObject();
	}
	
	public PropertyDetailsFragment(PropertyDetailsObject data) {
		mPropertyDetails = data;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContactUtil = new SendContactInfoUtil(getActivity());
		mViewHolder = inflater.inflate(R.layout.fragment_property_details, null);
		mInterestedButton = (Button) mViewHolder.findViewById(R.id.property_details_interested_btn);
		mImageButton = (ImageButton) mViewHolder.findViewById(R.id.property_details_header_image);
		mTitle = (TextView) mViewHolder.findViewById(R.id.property_details_title_text);
		mAddress = (TextView) mViewHolder.findViewById(R.id.property_details_address);
		mDescription = (TextView) mViewHolder.findViewById(R.id.property_details_description);
		mPrice = (TextView) mViewHolder.findViewById(R.id.property_details_price);
		
		mTitle.setText(mPropertyDetails.getPropertyTitle());
		mAddress.setText(mPropertyDetails.getPropertyAddress());
		mDescription.setText(mPropertyDetails.getPropertyDescription());
		mPrice.setText(mPropertyDetails.getPropertyPrice());
		//TODO set the image button background from available image url
		//TODO set the number of room count
						
		mAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String uri = "geo:0,0?q=" + mAddress.getText().toString();
					getActivity().startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), R.string.error_maps_app, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		mInterestedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mContactUtil.getContactedDialog();
			}
		});
		
		mImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showGallery();
			}
		});
		return mViewHolder;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void showGallery() {
		GalleryDialogFragment dialog = new GalleryDialogFragment();
		if(mPropertyDetails.getPropertyImagesURL() != null && mPropertyDetails.getPropertyImagesURL().length > 0) {
			Bundle args = new Bundle();
			args.putStringArray(GalleryDialogFragment.ARG_IMAGE_URLS, mPropertyDetails.getPropertyImagesURL());
			dialog.setArguments(args);
		}
		dialog.show(getChildFragmentManager(), "galleryDialog");
	}
}
