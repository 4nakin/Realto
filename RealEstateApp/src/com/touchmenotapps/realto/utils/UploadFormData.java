package com.touchmenotapps.realto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.interfaces.DataUploadListener;
import com.touchmenotapps.realto.model.PropertyDetailsObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadFormData extends AsyncTask<PropertyDetailsObject, Void, Integer>{

	private final int ERROR_NO_CONNECTION = 1;
	private final int ERROR_EXCEPTION = ERROR_NO_CONNECTION + 1;
	private final int SUCCESS = ERROR_NO_CONNECTION + 2;
	private final String URL = "";
	
	private ProgressDialog mProgressDialog;
	private NetworkUtil mNetworkUtils;
	private Context mContext;
	private DataUploadListener mCallback;
	
	public UploadFormData(Context context, DataUploadListener callback) {
		mContext = context;
		mCallback = callback;
		mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		mNetworkUtils = new NetworkUtil();
		mProgressDialog.setCancelable(true);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(PropertyDetailsObject... params) {
		if(mNetworkUtils.isNetworkAvailable(mContext)) {
			try {
				PostMethod method;
				method = new PostMethod(URL);
				//Add the items to be uploaded
				ArrayList<Part> mDataParts = new ArrayList<Part>();
				mDataParts.add(new StringPart("", params[0].getPropertyTitle()));
				mDataParts.add(new StringPart("", params[0].getPropertyAddress()));
				mDataParts.add(new StringPart("", params[0].getPropertyDescription()));
				mDataParts.add(new StringPart("", params[0].getPropertyPrice()));
				mDataParts.add(new StringPart("", params[0].getPropertyUploaderMail()));
				/** Check if there is any room data or not **/
				if(params[0].getPropertyRoomCount() != null)
					for(String rooms : params[0].getPropertyRoomCount())
						mDataParts.add(new StringPart("", rooms));
				/** Check if there is any images data or not **/
				if(params[0].getPropertyImagesURL() != null) 
					for(String images : params[0].getPropertyImagesURL()) {
						FilePart photo = new FilePart("", new ByteArrayPartSource(images, getBytesFromFile(new File(images))));
						photo.setContentType("image/jpeg");
						photo.setCharSet(null);
						mDataParts.add(photo);
					}
				method.setRequestEntity(new MultipartRequestEntity(mDataParts.toArray(new Part[mDataParts.size()]), method.getParams()));
				Log.e("httpPost", "Response status: " + method.toString());
				
				HttpClient client = new HttpClient();
				client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
				client.executeMethod(method);
				method.releaseConnection();
				Log.e("httpPost", "Response status: " + method.getResponseBodyAsString());
				if (method.getResponseBodyAsString().equals("SUCCESS")) {
					return SUCCESS;
				} else {
					return ERROR_EXCEPTION;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR_EXCEPTION;
			}
		} else
			return ERROR_NO_CONNECTION;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		mProgressDialog.dismiss();
		switch (result) {
		case ERROR_NO_CONNECTION:
			mNetworkUtils.showNetworkErrorDialog(mContext);
			//Toast.makeText(mContext, R.string.error_no_network, Toast.LENGTH_LONG).show();
			break;
		case ERROR_EXCEPTION:
			Toast.makeText(mContext, R.string.error_uploading, Toast.LENGTH_LONG).show();
			break;
		case SUCCESS:
			mCallback.onUploadSuccessful();
			break;
		}
	}
	
	/**
	 * Simple Reads the image file and converts them to Bytes
	 * 
	 * @param file name of the file
	 * @return byte array which is converted from the image
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	    // Get the size of the file
	    long length = file.length();
	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];
	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
}
