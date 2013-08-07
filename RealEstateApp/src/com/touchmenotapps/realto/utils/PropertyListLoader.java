package com.touchmenotapps.realto.utils;

import java.util.ArrayList;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.touchmenotapps.realto.model.PropertyDetailsObject;

public class PropertyListLoader extends AsyncTaskLoader<ArrayList<PropertyDetailsObject>> {

	private final String URL = "http://appztiger.com/demo/wordpress/property_list.php";
	private final String FILTER_URL = "http://appztiger.com/demo/wordpress/search.php";
	private NetworkUtil mNetworkUtil;
	private Context mContext;
	private ArrayList<NameValuePair> mData = null;
	private SAXParserFactory factory;
	private SAXParser parser;
	private XMLReader xmlreader;
	private ServerResponseHandler mResponseHandler;
	private InputSource is;
	
	public PropertyListLoader(Context context, ArrayList<NameValuePair> data) {
		super(context);
		this.mData = data;
		mNetworkUtil = new NetworkUtil();
		mContext = context;
		/** Init the sax parser **/
		factory = SAXParserFactory.newInstance();
        try {
			parser = factory.newSAXParser();
			xmlreader = parser.getXMLReader();
	        mResponseHandler=new ServerResponseHandler();
	        xmlreader.setContentHandler(mResponseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}         
	}

	@Override
	public ArrayList<PropertyDetailsObject> loadInBackground() {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
				if(mData == null) {
					Log.i("Test", "Show All");
					URL url= new URL(URL);  
					is = new InputSource(url.openStream());
				} else {
					HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost(FILTER_URL);		        
			        httppost.setEntity(new UrlEncodedFormEntity(mData));
			        HttpResponse response = httpclient.execute(httppost);
			        String response_string = EntityUtils.toString(response.getEntity());
			        Log.i("postData", response_string);
			        is = new InputSource(new StringReader(response_string));
				}
                xmlreader.parse(is);
                //InputStream in = mContext.getResources().openRawResource(R.raw.sample);
                //xmlreader.parse(new InputSource(in));
                return mResponseHandler.getData();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<PropertyDetailsObject>();
            }
		} else 
			return new ArrayList<PropertyDetailsObject>();
	}
}
