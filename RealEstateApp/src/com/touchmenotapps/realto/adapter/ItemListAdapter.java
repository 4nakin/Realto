package com.touchmenotapps.realto.adapter;

import java.util.ArrayList;

import com.touchmenotapps.realto.R;
import com.touchmenotapps.realto.model.PropertyDetailsObject;

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
		mData = data;
		notifyDataSetChanged();
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
		final ViewHolder holder;
		if (convertView == null) {
			/** Initialize the adapter widgets **/
			convertView = inflater.inflate(R.layout.adapter_item_list, null);
			holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.list_header_text);
			holder.mAddress = (TextView) convertView.findViewById(R.id.list_sub_header_text);
			holder.mPrice = (TextView) convertView.findViewById(R.id.list_price_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTitle.setText(mData.get(position).getPropertyTitle());
		holder.mAddress.setText(mData.get(position).getPropertyAddress());
		holder.mPrice.setText(mData.get(position).getPropertyPrice() + " " + mData.get(position).getCurrency());
		return convertView;
	}
	
	static class ViewHolder {
		TextView mTitle, mAddress, mPrice;
	}
}
