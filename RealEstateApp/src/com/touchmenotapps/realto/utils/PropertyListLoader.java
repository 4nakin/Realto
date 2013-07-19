package com.touchmenotapps.realto.utils;

import java.util.ArrayList;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.touchmenotapps.realto.model.PropertyDetailsObject;

public class PropertyListLoader extends AsyncTaskLoader<ArrayList<PropertyDetailsObject>> {

	public static final String GET_ALL = "ALL";
	private final String URL = "http://appztiger.com/demo/wordpress/property_list.php";
	private final String FILTER_URL = "";
	private NetworkUtil mNetworkUtil;
	private Context mContext;
	private String query;
	private SAXParserFactory factory;
	private SAXParser parser;
	private XMLReader xmlreader;
	private ServerResponseHandler mResponseHandler;
	private InputSource is;
	
	public PropertyListLoader(Context context, String query) {
		super(context);
		this.query = query;
		mNetworkUtil = new NetworkUtil();
		mContext = context;
		
	}

	@Override
	public ArrayList<PropertyDetailsObject> loadInBackground() {
		if(mNetworkUtil.isNetworkAvailable(mContext)) {
			try {
				/** Init the sax parser **/
				factory = SAXParserFactory.newInstance();
                parser = factory.newSAXParser();
                xmlreader = parser.getXMLReader();
                mResponseHandler=new ServerResponseHandler();
                xmlreader.setContentHandler(mResponseHandler);
                /** Make the requests **/
				if(query.equals(GET_ALL)) {
					URL url= new URL(URL);  
					is = new InputSource(url.openStream());
				} else {
					HttpPost httpPost = new HttpPost(FILTER_URL);
				    StringEntity entity;
				    String response_string = null;
					entity = new StringEntity(query, HTTP.UTF_8);
			        httpPost.setHeader("Content-Type","text/xml;charset=UTF-8");
			        httpPost.setEntity(entity);
			        HttpClient client = new DefaultHttpClient();
			        HttpResponse response = client.execute(httpPost);
			        response_string = EntityUtils.toString(response.getEntity());
			        Log.i(getClass().getName(), response_string);
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
