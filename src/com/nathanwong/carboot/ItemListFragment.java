package com.nathanwong.carboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ItemListFragment extends ListFragment {
	private final static String TAG = "ItemListFragment";
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ParseQuery listQuery = new ParseQuery("listItems");
		listQuery.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> itemList, ParseException e) {
				if (e == null) {
					setupListView(itemList);
				} else {
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
			
		});
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.home, menu);
	}
    
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    	
    	if (! ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
	    	MenuItem item = menu.findItem(R.id.menu_action_register);
	    	item.setEnabled(false);
	    	item.setVisible(false);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_register):
    			Intent intent = new Intent(getActivity(), RegisterActivity.class);
    			startActivity(intent);
    			
    			return true;
    		
    		default: return false;
    	}
    }
    
    private void setupListView (List<ParseObject> items) {
    	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(2);
    	
    	HashMap<String, String> map;
    	for (ParseObject object : items) {
    		map = new HashMap<String, String>();
    		map.put("title", object.getString("title"));
    		
    		list.add(map);
    	}
    	
    	String[] from = {"title"};
    	int[] to = {android.R.id.text1};
    	
    	SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1, from, to);
    	setListAdapter(adapter);
    }
}
