package com.touchmenotapps.realto.fragments;

import java.io.InputStream;
import java.net.URL;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.model.PropertyDetailsObject;
import com.touchmenotapps.realto.utils.NetworkUtil;
import com.touchmenotapps.realto.utils.SendContactInfoUtil;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class PropertyDetailsFragment extends Fragment{

	private View mViewHolder;
	private Button mInterestedButton;
	private ImageButton mImageButton;
	private TextView mTitle, mAddress, mDescription, mPrice, mBedroom, mBathroom, mKitchen, mHall;
	private SendContactInfoUtil mContactUtil;
	private PropertyDetailsObject mPropertyDetails;
	private NetworkUtil mNetworkUtil;
	
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
		mNetworkUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_property_details, null);
		mInterestedButton = (Button) mViewHolder.findViewById(R.id.property_details_interested_btn);
		mImageButton = (ImageButton) mViewHolder.findViewById(R.id.property_details_header_image);
		mTitle = (TextView) mViewHolder.findViewById(R.id.property_details_title_text);
		mAddress = (TextView) mViewHolder.findViewById(R.id.property_details_address);
		mDescription = (TextView) mViewHolder.findViewById(R.id.property_details_description);
		mPrice = (TextView) mViewHolder.findViewById(R.id.property_details_price);
		mBedroom = (TextView) mViewHolder.findViewById(R.id.property_detials_bedroom_count);
		mBathroom = (TextView) mViewHolder.findViewById(R.id.property_detials_bathroom_count);
		mKitchen = (TextView) mViewHolder.findViewById(R.id.property_detials_kitchen_count);
		mHall = (TextView) mViewHolder.findViewById(R.id.property_detials_hall_count);
		
		mTitle.setText(mPropertyDetails.getPropertyTitle());
		mAddress.setText(mPropertyDetails.getPropertyAddress());
		mDescription.setText(mPropertyDetails.getPropertyDescription());
		mPrice.setText(mPropertyDetails.getPropertyPrice());
		if(mPropertyDetails.getPropertyRoomCount() != null) {
			mBedroom.setText(mPropertyDetails.getPropertyRoomCount()[0]);
			mBathroom.setText(mPropertyDetails.getPropertyRoomCount()[1]);
			mKitchen.setText(mPropertyDetails.getPropertyRoomCount()[2]);
			mHall.setText(mPropertyDetails.getPropertyRoomCount()[3]);
		}
		if(mPropertyDetails.getPropertyImagesURL() != null && mPropertyDetails.getPropertyImagesURL().length > 0) {
			if(mNetworkUtil.isNetworkAvailable(getActivity()))
				new LoadImageFromWebOperations().execute(mPropertyDetails.getPropertyImagesURL()[0]);
			else
				mImageButton.setBackgroundResource(R.drawable.ic_broken_file);
		}
						
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
				if(mNetworkUtil.isNetworkAvailable(getActivity()))
					showGallery();
				else
					Toast.makeText(getActivity(), R.string.error_no_network_gallery, Toast.LENGTH_LONG).show();
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
