package com.touchmenotapps.realto.fragments;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.adapter.ItemListAdapter;
import com.touchmenotapps.realto.model.PropertyDetailsObject;
import com.touchmenotapps.realto.utils.NetworkUtil;
import com.touchmenotapps.realto.utils.ServerResponseHandler;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DataListFragment extends ListFragment {
	
	private ItemListAdapter mAdapter;
	private OnPropertySelectedListener mCallback;
	private NetworkUtil mNetworkUtil;

	public interface OnPropertySelectedListener {
		public void onPropertyListClicked(PropertyDetailsObject mData);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNetworkUtil = new NetworkUtil();
		mAdapter = new ItemListAdapter(getActivity());
	}

	public void getServerPropertyData() {
		// Set the adapter
		if (mNetworkUtil.isNetworkAvailable(getActivity())) {
			new RetreiveFeedTask().execute("");
		} else
			mNetworkUtil.showNetworkErrorDialog(getActivity());
	}

	@Override
	public void onResume() {
		super.onResume();
		getListView().setPadding(
				getResources().getDimensionPixelSize(R.dimen.upload_gallery_image_margin), 0, 
				getResources().getDimensionPixelSize(R.dimen.upload_gallery_image_margin), 0);
		getServerPropertyData();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnPropertySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		mCallback.onPropertyListClicked(mAdapter.getItem(position));
	}
	
	class RetreiveFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                //URL url= new URL(urls[0]);
                SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                
                ServerResponseHandler mResponseHandler=new ServerResponseHandler();
                xmlreader.setContentHandler(mResponseHandler);
                //InputSource is=new InputSource(url.openStream());
                InputStream is = getResources().openRawResource(R.raw.sample);
                xmlreader.parse(new InputSource(is));
                mAdapter.setListData(mResponseHandler.getData());
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String feed) {
        	if(feed != null) 
        		if(mAdapter.getCount() > 0) {
        			setListAdapter(mAdapter);
        			if(getActivity().findViewById(R.id.property_details_fragment_container) != null) 
        				mCallback.onPropertyListClicked(mAdapter.getItem(0));
        		}
        	else {
        		Toast.makeText(getActivity(), R.string.error_server_data_fetching, Toast.LENGTH_LONG).show();
        	}
        }
     }
}
