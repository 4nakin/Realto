package com.touchmenotapps.realto.utils;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.model.PropertyDetailsObject;

public class AppListLoader extends AsyncTaskLoader<ArrayList<PropertyDetailsObject>> {

	@SuppressWarnings("unused")
	private final String URL = "";
	private NetworkUtil mNetworkUtil;
	private Context mContext;
	
	public AppListLoader(Context context) {
		super(context);
		mNetworkUtil = new NetworkUtil();
		mContext = context;
	}

	@Override
	public ArrayList<PropertyDetailsObject> loadInBackground() {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
                //URL url= new URL(urls[0]);
                SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                
                ServerResponseHandler mResponseHandler=new ServerResponseHandler();
                xmlreader.setContentHandler(mResponseHandler);
                //InputSource is=new InputSource(url.openStream());
                InputStream is = mContext.getResources().openRawResource(R.raw.sample);
                xmlreader.parse(new InputSource(is));
                return mResponseHandler.getData();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<PropertyDetailsObject>();
            }
		} else 
			return new ArrayList<PropertyDetailsObject>();
	}
}
