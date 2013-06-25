package com.touchmenotapps.realto.utils;

import java.util.ArrayList;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.touchmenotapps.realto.model.PropertyDetailsObject;

public class PropertyListLoader extends AsyncTaskLoader<ArrayList<PropertyDetailsObject>> {

	private final String URL = "http://appztiger.com/demo/wordpress/property_list.php";
	private NetworkUtil mNetworkUtil;
	private Context mContext;
	
	public PropertyListLoader(Context context) {
		super(context);
		mNetworkUtil = new NetworkUtil();
		mContext = context;
	}

	@Override
	public ArrayList<PropertyDetailsObject> loadInBackground() {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
                URL url= new URL(URL);
                SAXParserFactory factory =SAXParserFactory.newInstance();
                SAXParser parser=factory.newSAXParser();
                XMLReader xmlreader=parser.getXMLReader();
                
                ServerResponseHandler mResponseHandler=new ServerResponseHandler();
                xmlreader.setContentHandler(mResponseHandler);
                InputSource is=new InputSource(url.openStream());
                //InputStream is = mContext.getResources().openRawResource(R.raw.sample);
                //xmlreader.parse(new InputSource(is));
                xmlreader.parse(is);
                return mResponseHandler.getData();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<PropertyDetailsObject>();
            }
		} else 
			return new ArrayList<PropertyDetailsObject>();
	}
}
