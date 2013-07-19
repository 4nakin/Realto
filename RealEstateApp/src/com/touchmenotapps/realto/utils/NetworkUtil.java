package com.touchmenotapps.realto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.touchmenotapps.realto.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Base64;

/**
 * 
 * @author arindam
 *
 */
public class NetworkUtil {

	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/**
	 * 
	 * @param context
	 */
	public void showNetworkErrorDialog(final Context context) {
		final AlertDialog networkErrorAlertDialog = new AlertDialog.Builder(context, 
				AlertDialog.THEME_DEVICE_DEFAULT_DARK).create();
		networkErrorAlertDialog.setCanceledOnTouchOutside(false);
		networkErrorAlertDialog.setTitle(R.string.network_error);
		networkErrorAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, 
				context.getResources().getString(R.string.settings), 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				networkErrorAlertDialog.dismiss();
			}
		});
		
		networkErrorAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, 
				context.getResources().getString(R.string.cancel), 
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				networkErrorAlertDialog.dismiss();
			}
		});
		networkErrorAlertDialog.show();
	}
	
	/**
	 * Simple Reads the image file and converts them to Bytes
	 * and then encodes it to base 64
	 * @param file name of the file
	 * @return byte array which is converted from the image
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public String getEncodedBase64StringFromFile(File file) throws IOException {
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
	    return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
}
