package com.nathanwong.carboot;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseObject;

public class ItemArrayAdapter extends ArrayAdapter<ParseObject> {
	
	int resource;
	
	public ItemArrayAdapter (Context context,
								int _resource,
								List<ParseObject> items) {
		super(context, _resource, items);
		resource = _resource;
	}

	@Override
	public View getView (int position, View convertView, ViewGroup parent) {
		// Create and inflate view
		
		LinearLayout row;
		ParseObject obj = getItem(position);
		
		if (convertView == null) {
			// We aren't updating an existing row, so inflate it anew
			row = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater) getContext().getSystemService(inflater);
			li.inflate(resource, row, true);
		} else {
			row = (LinearLayout) convertView;
		}
		
		TextView title = (TextView) row.findViewById(R.id.list_row_text);
		
		title.setText(obj.getString("title"));
		
		return row;
	}
}