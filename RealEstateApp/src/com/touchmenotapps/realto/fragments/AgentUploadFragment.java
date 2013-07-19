package com.touchmenotapps.realto.fragments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.interfaces.DataUploadListener;
import com.touchmenotapps.realto.utils.NetworkUtil;
import com.touchmenotapps.realto.utils.UploadFormData;

import android.annotation.SuppressLint;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class AgentUploadFragment extends Fragment implements DataUploadListener {

	private final int TAG_GALLERY = 0;
	private final int TAG_CAMERA = 1;
	private final int GALLERY_REQUEST = 23;
	private final int CAMERA_REQUEST = GALLERY_REQUEST + 1;
	private final int MAX_IMAGES = 5;
	
	private View mViewHolder;
	private EditText mPropertyName, mPropertyPrice, mPropertyDescription, mPropertyLocation;
	private Spinner mBedroomSpinner, mBathroomSpinner;
	private Button mAddImageBtn;
	private LinearLayout mRoomsListView;
	private ImageView[] mGalleryImages = new ImageView[MAX_IMAGES];
	private File mImageFile;
	private ArrayList<String> mImagePaths = new ArrayList<String>();
	private ArrayList<CheckBox> mRoomButtons = new ArrayList<CheckBox>();
	private NetworkUtil mNetworkUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mNetworkUtil = new NetworkUtil();
		mViewHolder = inflater.inflate(R.layout.fragment_agent_upload, null);
		
		mPropertyName = (EditText) mViewHolder.findViewById(R.id.upload_property_name);
		mPropertyPrice = (EditText) mViewHolder.findViewById(R.id.upload_enter_price);
		mPropertyDescription = (EditText) mViewHolder.findViewById(R.id.upload_description);
		mPropertyLocation = (EditText) mViewHolder.findViewById(R.id.upload_enter_address);
		mAddImageBtn = (Button) mViewHolder.findViewById(R.id.upload_add_image_btn);
		mBedroomSpinner = (Spinner) mViewHolder.findViewById(R.id.upload_bedrooms);
		mBathroomSpinner = (Spinner) mViewHolder.findViewById(R.id.upload_bathrooms);
		mGalleryImages[0] = (ImageView) mViewHolder.findViewById(R.id.upload_image_1);
		mGalleryImages[1] = (ImageView) mViewHolder.findViewById(R.id.upload_image_2);
		mGalleryImages[2] = (ImageView) mViewHolder.findViewById(R.id.upload_image_3);
		mGalleryImages[3] = (ImageView) mViewHolder.findViewById(R.id.upload_image_4);
		mGalleryImages[4] = (ImageView) mViewHolder.findViewById(R.id.upload_image_5);
		mRoomsListView = (LinearLayout) mViewHolder.findViewById(R.id.upload_other_rooms_list);
		/** Init the other other rooms section **/
		setOtherRoomOptions();
		
		mViewHolder.findViewById(R.id.upload_discard_btn).setOnClickListener(new OnClickListener() {
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

		mViewHolder.findViewById(R.id.upload_start_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new UploadFormData(getActivity(), AgentUploadFragment.this).execute(new String[] { setServerResponse() });
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
				clearAllFields();
			}
		});
		mDiscardDialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {  
			//Retrieve image path from the file we created during the event of taking image
            Bitmap photo = BitmapFactory.decodeFile(mImageFile.getAbsolutePath()); 
            mImagePaths.add(mImageFile.getAbsolutePath()); //Add image path as part of the array list
            mGalleryImages[mImagePaths.size()-1].setImageBitmap(photo);
            mGalleryImages[mImagePaths.size()-1].setVisibility(View.VISIBLE);
            //Check the number of images already selected
            if(mImagePaths.size() == MAX_IMAGES) 
            	mAddImageBtn.setVisibility(View.GONE);            	
        }  else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && null != data) {
        	Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            mImagePaths.add(picturePath);//Add image path as part of the array list
            cursor.close();
            mGalleryImages[mImagePaths.size()-1].setImageBitmap(BitmapFactory.decodeFile(picturePath));
            mGalleryImages[mImagePaths.size()-1].setVisibility(View.VISIBLE);
            //Check the number of images already selected
            if(mImagePaths.size() == MAX_IMAGES) 
            	mAddImageBtn.setVisibility(View.GONE);
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
		        	//Create custom image path
		        	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+".jpg";
		        	mImageFile = new File(
		        		    Environment.getExternalStoragePublicDirectory(
		        		        Environment.DIRECTORY_PICTURES), timeStamp); 
		        	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		        	cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
	                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
		        	break;
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void clearAllFields() {
		mPropertyName.getEditableText().clear();
		mPropertyPrice.getEditableText().clear();
		mPropertyDescription.getEditableText().clear();
		mPropertyLocation.getEditableText().clear();
		mAddImageBtn.setVisibility(View.VISIBLE);
		mBedroomSpinner.setSelection(0);
		mBathroomSpinner.setSelection(0);
		for(CheckBox button : mRoomButtons) 
			button.setChecked(false);
		for(int i = 0; i < MAX_IMAGES; i++) 
			mGalleryImages[i].setVisibility(View.GONE);
		mImagePaths.clear();
	}

	@Override
	public void onUploadSuccessful() {
		clearAllFields();
	}
	
	private String setServerResponse() {
		/** Add the list of rooms **/
		String rooms = "<bedroom>" + mBedroomSpinner.getSelectedItem().toString() + "</bedroom>" + 
				"<bathroom>" + mBathroomSpinner.getSelectedItem().toString() + "</bathroom>";
		for(CheckBox mRoom : mRoomButtons) {
			if(mRoom.isChecked())
				rooms = rooms + "<" +  mRoom.getText().toString().replaceAll("\\s", "").toLowerCase() + 
				">" + 1 + "</"+  mRoom.getText().toString().replaceAll("\\s", "").toLowerCase() + ">";
			else
				rooms = rooms + "<" +  mRoom.getText().toString().replaceAll("\\s", "").toLowerCase() + 
				">" + 0 + "</"+  mRoom.getText().toString().replaceAll("\\s", "").toLowerCase() + ">";
		}
		/** Add the list of images **/
		String images = "";
		if(mImagePaths.size() > 0) {
			for(String path : mImagePaths) {
				try {
					images = images + "<image>" + mNetworkUtil.getEncodedBase64StringFromFile(new File(path)) + "</image>";
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/** The main response string body **/
		String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
				"<title>" + mPropertyName.getText().toString().trim() + "</title>" +
				"<address>"+ mPropertyLocation.getText().toString().trim() + "</address>" +
				"<price>" + mPropertyPrice.getText().toString().trim() + "</price>" + 
				"<description>" + mPropertyDescription.getText().toString().trim() + "</description>"+
				"<contact> dummy@dummy.com </contact>"+
				"<rooms>" + rooms + " </rooms>" + 
				"<images>" + images + "</images>";
		Log.i(getClass().getName(), response);
		return response;
	}

	public void setOtherRoomOptions() {
		String[] data = getResources().getStringArray(R.array.other_rooms);
		for(int i = 0; i < data.length; i++) {
			CheckBox option = new CheckBox(getActivity());
			option.setText(data[i]);
			option.setPadding(0, 10, 0, 10);
			option.setTextColor(getResources().getColor(R.color.body_text_color));
			mRoomsListView.addView(option);
			mRoomButtons.add(option);
		}
	}
}
