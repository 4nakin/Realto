package com.touchmenotapps.realto.fragments;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.utils.NetworkUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AgentUploadFragment extends Fragment {

	private final int TAG_GALLERY = 0;
	private final int TAG_CAMERA = 1;
	private final int GALLERY_REQUEST = 23;
	private final int CAMERA_REQUEST = GALLERY_REQUEST + 1;
	
	private int imageCount = 0;
	private View mViewHolder;
	private EditText mPropertyName, mPropertyPrice, mPropertyDescription;
	private Button mSelectLocationBtn, mAddImageBtn, mUploadBtn, mDiscardBtn;
	private ImageView[] mGalleryImages = new ImageView[5];
	private NetworkUtil mNetworkUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mNetworkUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_agent_upload, null);
		mPropertyName = (EditText) mViewHolder.findViewById(R.id.upload_property_name);
		mPropertyPrice = (EditText) mViewHolder.findViewById(R.id.upload_enter_price);
		mPropertyDescription = (EditText) mViewHolder.findViewById(R.id.upload_description);
		mSelectLocationBtn = (Button) mViewHolder.findViewById(R.id.upload_select_place_btn);
		mAddImageBtn = (Button) mViewHolder.findViewById(R.id.upload_add_image_btn);
		mUploadBtn = (Button) mViewHolder.findViewById(R.id.upload_start_btn);
		mDiscardBtn = (Button) mViewHolder.findViewById(R.id.upload_discard_btn);
		mGalleryImages[0] = (ImageView) mViewHolder.findViewById(R.id.upload_image_1);
		mGalleryImages[1] = (ImageView) mViewHolder.findViewById(R.id.upload_image_2);
		mGalleryImages[2] = (ImageView) mViewHolder.findViewById(R.id.upload_image_3);
		mGalleryImages[3] = (ImageView) mViewHolder.findViewById(R.id.upload_image_4);
		mGalleryImages[4] = (ImageView) mViewHolder.findViewById(R.id.upload_image_5);
		
		mDiscardBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				clearEntry();
			}
		});

		mAddImageBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showImageMenu();
			}
		});

		mSelectLocationBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

			}
		});

		mUploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mNetworkUtil.isNetworkAvailable(getActivity())) {
					
				} else 
					mNetworkUtil.showNetworkErrorDialog(getActivity());
			}
		});

		return mViewHolder;
	}

	private void clearEntry() {
		final AlertDialog mDiscardDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK).create();
		mDiscardDialog.setTitle(R.string.discard_entry);
		mDiscardDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mDiscardDialog.show();
			}
		});
		mDiscardDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.discard), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mPropertyName.getEditableText().clear();
				mPropertyPrice.getEditableText().clear();
				mPropertyDescription.getEditableText().clear();
				mSelectLocationBtn.setText(R.string.select_location);
				mAddImageBtn.setVisibility(View.VISIBLE);
				for(int i = 0; i < 5; i++) 
					mGalleryImages[i].setVisibility(View.GONE);
				imageCount = 0;
			}
		});
		mDiscardDialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            mGalleryImages[imageCount].setImageBitmap(photo);
            mGalleryImages[imageCount].setVisibility(View.VISIBLE);
            if(imageCount >= 4) 
            	mAddImageBtn.setVisibility(View.GONE);
            else
            	imageCount++;
            	
        }  else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && null != data) {
        	Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            mGalleryImages[imageCount].setImageBitmap(BitmapFactory.decodeFile(picturePath));
            mGalleryImages[imageCount].setVisibility(View.VISIBLE);
            if(imageCount >= 4) 
            	mAddImageBtn.setVisibility(View.GONE);
            else
            	imageCount++;
        } 
	}
	
	private void showImageMenu() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builder.setItems(getResources().getStringArray(R.array.image_picker_options), new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        switch(item) {
		        case TAG_GALLERY:
		        	Intent galleryIntent = new Intent(Intent.ACTION_PICK, 
		        			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		 
		        	startActivityForResult(galleryIntent, GALLERY_REQUEST);
		        	break;
		        case TAG_CAMERA:
		        	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
		        	break;
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
