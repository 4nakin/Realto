package com.touchmenotapps.realto.utils;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.touchmenotapps.realto.model.PropertyDetailsObject;

public class ServerResponseHandler extends DefaultHandler {

	private final String TAG_PROPERTY_INFO = "property-info";
	private final String TAG_ID = "id";
	private final String TAG_TITLE = "title";
	private final String TAG_ADDRESS = "address";
	private final String TAG_PRICE = "price";
	private final String TAG_DESCRIPTION = "description";
	private final String TAG_CONTACT = "contact";
	
	String elementValue = null;
	Boolean elementOn = false;
	private PropertyDetailsObject mData;
	private ArrayList<String> mImageURLS = new ArrayList<String>();
	private ArrayList<String> mRoomDetails = new ArrayList<String>();
	private ArrayList<PropertyDetailsObject> mDataList = new ArrayList<PropertyDetailsObject>();
	
	public ArrayList<PropertyDetailsObject> getData() {
		return mDataList;
	}
	
	public void setData(ArrayList<PropertyDetailsObject> data) {
		this.mDataList = data;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		elementOn = true;
		if(localName.equals(TAG_PROPERTY_INFO))
			mData = new PropertyDetailsObject();
		else if(localName.equals("images-url"))
			mImageURLS.clear();
		else if(localName.equals("rooms-details"))
			mRoomDetails.clear();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		elementOn = false;
		if(localName.equals(TAG_ID))
			mData.setPropertyID(Integer.parseInt(elementValue));
		else if(localName.equals(TAG_TITLE))
			mData.setPropertyTitle(elementValue);
		else if(localName.equals(TAG_ADDRESS))
			mData.setPropertyAddress(elementValue);
		else if(localName.equals(TAG_PRICE))
			mData.setPropertyPrice(elementValue);
		else if(localName.equals(TAG_DESCRIPTION))
			mData.setPropertyDescription(elementValue);
		else if(localName.equals(TAG_CONTACT))
			mData.setPropertyUploaderMail(elementValue);
		else if(localName.equals("image"))
			mImageURLS.add(elementValue);
		else if(localName.equals("images-url"))
			mData.setPropertyImagesURL(mImageURLS.toArray(new String[mImageURLS.size()]));
		else if(localName.equals("room"))
			mRoomDetails.add(elementValue);
		else if(localName.equals("rooms-details"))
			mData.setPropertyRoomCount(mRoomDetails.toArray(new String[mRoomDetails.size()]));
		else if(localName.equals(TAG_PROPERTY_INFO))
			mDataList.add(mData);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (elementOn) {
			elementValue = new String(ch, start, length);
			elementOn = false;
		}
	}
}
