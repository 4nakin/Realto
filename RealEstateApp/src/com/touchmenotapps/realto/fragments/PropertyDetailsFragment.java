package com.touchmenotapps.realto.fragments;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.model.PropertyDetailsObject;
import com.touchmenotapps.realto.utils.NetworkUtil;
import com.touchmenotapps.realto.utils.SendContactInfoUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PropertyDetailsFragment extends Fragment{
	
	public static final String TAG_ID = "id";
	public static final String TAG_TITLE = "title";
	public static final String TAG_ADDRESS = "address";
	public static final String TAG_PRICE = "price";
	public static final String TAG_CURRENCY = "currency";
	public static final String TAG_DESCRIPTION = "description";
	public static final String TAG_CONTACT = "contact";
	public static final String TAG_IMAGES = "images";
	public static final String TAG_ROOMS = "rooms";

	private View mViewHolder;
	private ImageButton mImageButton;
	private SendContactInfoUtil mContactUtil;
	private PropertyDetailsObject mPropertyDetails;
	private NetworkUtil mNetworkUtil;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPropertyDetails = new PropertyDetailsObject();
		if(getArguments().containsKey(TAG_ADDRESS))
			mPropertyDetails.setPropertyAddress(getArguments().getString(TAG_ADDRESS));
		if(getArguments().containsKey(TAG_CONTACT))
			mPropertyDetails.setPropertyUploaderMail(getArguments().getString(TAG_CONTACT));
		if(getArguments().containsKey(TAG_CURRENCY))
			mPropertyDetails.setCurrency(getArguments().getString(TAG_CURRENCY));
		if(getArguments().containsKey(TAG_DESCRIPTION))
			mPropertyDetails.setPropertyDescription(getArguments().getString(TAG_DESCRIPTION));
		if(getArguments().containsKey(TAG_ID))
			mPropertyDetails.setPropertyID(getArguments().getString(TAG_ID));
		if(getArguments().containsKey(TAG_IMAGES))
			mPropertyDetails.setPropertyImagesURL(getArguments().getStringArray(TAG_IMAGES));
		if(getArguments().containsKey(TAG_PRICE))
			mPropertyDetails.setPropertyPrice(getArguments().getString(TAG_PRICE));
		if(getArguments().containsKey(TAG_ROOMS))
			mPropertyDetails.setPropertyRoomCount(getArguments().getStringArray(TAG_ROOMS));
		if(getArguments().containsKey(TAG_TITLE))
			mPropertyDetails.setPropertyTitle(getArguments().getString(TAG_TITLE));
	}
			
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContactUtil = new SendContactInfoUtil(getActivity());
		mNetworkUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_property_details, null);
		mImageButton = (ImageButton) mViewHolder.findViewById(R.id.property_details_header_image);
		initView();
			
		mViewHolder.findViewById(R.id.property_details_address).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String uri = "geo:0,0?q=" + mPropertyDetails.getPropertyAddress();
					getActivity().startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), R.string.error_maps_app, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		mViewHolder.findViewById(R.id.property_details_interested_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mContactUtil.getContactedDialog();
			}
		});
		
		mImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mNetworkUtil.isNetworkAvailable(getActivity()))
					showGallery();
				else
					Toast.makeText(getActivity(), R.string.error_no_network_gallery, Toast.LENGTH_LONG).show();
			}
		});
		return mViewHolder;
	}
	
	private void initView() {
		((TextView) mViewHolder.findViewById(R.id.property_details_title_text))
		.setText(mPropertyDetails.getPropertyTitle());
	((TextView) mViewHolder.findViewById(R.id.property_details_address))
		.setText(mPropertyDetails.getPropertyAddress());
	((TextView) mViewHolder.findViewById(R.id.property_details_description))
		.setText(mPropertyDetails.getPropertyDescription());
	((TextView) mViewHolder.findViewById(R.id.property_details_price))
		.setText(mPropertyDetails.getPropertyPrice());
	((TextView) mViewHolder.findViewById(R.id.property_details_price_currency))
		.setText(getString(R.string.price) + " (" + mPropertyDetails.getCurrency() + ")");
	
	if(mPropertyDetails.getPropertyRoomCount() != null) {
		((TextView) mViewHolder.findViewById(R.id.property_detials_bedroom_count))
			.setText(mPropertyDetails.getPropertyRoomCount()[0]);
		((TextView) mViewHolder.findViewById(R.id.property_detials_bathroom_count))
			.setText(mPropertyDetails.getPropertyRoomCount()[1]);
		((TextView) mViewHolder.findViewById(R.id.property_detials_kitchen_count))
			.setText(mPropertyDetails.getPropertyRoomCount()[2]);
		((TextView) mViewHolder.findViewById(R.id.property_detials_hall_count))
			.setText(mPropertyDetails.getPropertyRoomCount()[3]);
	}
	
	if(mPropertyDetails.getPropertyImagesURL() != null && mPropertyDetails.getPropertyImagesURL().length > 0) {
		if(mNetworkUtil.isNetworkAvailable(getActivity()))
			new LoadImageFromWebOperations().execute(mPropertyDetails.getPropertyImagesURL()[0]);
		else
			mImageButton.setBackgroundResource(R.drawable.ic_broken_file);
	}
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

		protected void onPostExecute(Drawable drawable) {
        	if(drawable != null) 
        		mImageButton.setImageDrawable(drawable);
        	else
        		mImageButton.setImageResource(R.drawable.ic_broken_file);
        }
     }
}
