package com.touchmenotapps.realto.interfaces;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

/**
 * @author arindam
 *
 */
public interface OnSearchListener {
	public void onSearchClicked(ArrayList<NameValuePair> data);
	public void onSearchAllClicked();
}
