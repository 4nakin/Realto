package com.example.realestateapp.adapter;

import java.util.ArrayList;

import com.example.realestateapp.R;
import com.example.realestateapp.model.PropertyDetailsObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private ArrayList<PropertyDetailsObject> mData = new ArrayList<PropertyDetailsObject>();

	public ItemListAdapter(Context context) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void setListData(ArrayList<PropertyDetailsObject> data ) {
		notifyDataSetChanged();
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public PropertyDetailsObject getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView mTitle, mAddress, mPrice;
		if (convertView == null) {
			/** Initialize the adapter widgets **/
			convertView = inflater.inflate(R.layout.adapter_item_list, null);
			mTitle = (TextView) convertView.findViewById(R.id.list_header_text);
			mAddress = (TextView) convertView.findViewById(R.id.list_sub_header_text);
			mPrice = (TextView) convertView.findViewById(R.id.list_price_text);
			mTitle.setText(mData.get(position).getPropertyTitle());
			mAddress.setText(mData.get(position).getPropertyAddress());
			mPrice.setText(mData.get(position).getPropertyPrice() + " USD");
		} 
		return convertView;
	}
}
