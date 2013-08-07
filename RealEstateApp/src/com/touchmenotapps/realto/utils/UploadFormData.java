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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadFormData extends AsyncTask<String, Void, Integer>{

	private final int ERROR_NO_CONNECTION = 1;
	private final int ERROR_EXCEPTION = ERROR_NO_CONNECTION + 1;
	private final int SUCCESS = ERROR_NO_CONNECTION + 2;
	private final String URL = "http://appztiger.com/demo/wordpress/upload.php";
	
	private ProgressDialog mProgressDialog;
	private NetworkUtil mNetworkUtils;
	private Context mContext;
	private DataUploadListener mCallback;
	
	public UploadFormData(Context context, DataUploadListener callback) {
		mContext = context;
		mCallback = callback;
		mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		mNetworkUtils = new NetworkUtil();
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage(context.getText(R.string.uploading));
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		if(mNetworkUtils.isNetworkAvailable(mContext)) {
			String responseString = null;
	        PostMethod method = new PostMethod(URL);
	        HttpClient client = new HttpClient();
	        client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
			try {
				if(formRequest(params).size() > 0) {
					method.setRequestEntity(new MultipartRequestEntity(
							formRequest(params).toArray(new Part[formRequest(params).size()]), 
							method.getParams()));
					client.executeMethod(method);
					responseString = method.getResponseBodyAsString();
					method.releaseConnection();
					Log.i("Upload", "Res: " + responseString);
			        if(responseString.equals("success"))
			        	return SUCCESS;
			        else
			        	return ERROR_EXCEPTION;
				} else 
					return ERROR_EXCEPTION;
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
	
	private ArrayList<Part> formRequest(String[] params) throws Exception {
		ArrayList<Part> parts = new ArrayList<Part>();
		parts.add(new StringPart("title", params[0]));
		parts.add(new StringPart("location", params[1]));
		parts.add(new StringPart("price", params[2]));
		parts.add(new StringPart("description", params[3]));
		parts.add(new StringPart("bedroom", params[4]));
		parts.add(new StringPart("bathroom", params[5]));
		parts.add(new StringPart("kitchen", params[6]));
		parts.add(new StringPart("living-room", params[7]));
		parts.add(new StringPart("covered-car-garage", params[8]));
		parts.add(new StringPart("swimming-pool", params[9]));
		parts.add(new StringPart("basement", params[10]));
		parts.add(new StringPart("attic", params[11]));
		//Add the images
		if((params.length - 12) >0) {
			for(int i = 12; i < params.length; i++) {
				FilePart photo = new FilePart("file", new ByteArrayPartSource(
						"image"+String.valueOf(i), getBytesFromFile(new File(params[i]))));
				photo.setContentType("image/jpeg");
                photo.setCharSet(null);
                parts.add(photo);
			}
		}
		return parts;
	}
	
	/**
	 * Simple Reads the image file and converts them to Bytes
	 * 
	 * @param file name of the file
	 * @return byte array which is converted from the image
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws Exception {
	    InputStream is = new FileInputStream(file);
	    // Get the size of the file
	    long length = file.length();
	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    	throw new IOException("The file  "+file.getName() + " is too long.");
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
	    Log.i("Photo", "Upload " + new String(bytes));
	    return bytes;
	}
}
