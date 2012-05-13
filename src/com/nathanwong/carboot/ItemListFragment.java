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
	
	MenuItem refreshItem;
	
	private final static String TAG = "ItemListFragment";
	private ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>(2);
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
    	
    	populateItems();
    	
	}
	
	@Override
	public void onResume () {
		super.onResume();
		
		getActivity().invalidateOptionsMenu();
		populateItems();
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.home, menu);
	}
    
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    	
    	refreshItem = menu.findItem(R.id.menu_action_list_refresh);
    	
    	if (! ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
	    	MenuItem registerItem = menu.findItem(R.id.menu_action_register);
	    	registerItem.setEnabled(false);
	    	registerItem.setVisible(false);
	    	
	    	MenuItem loginItem = menu.findItem(R.id.menu_action_login);
	    	loginItem.setEnabled(false);
	    	loginItem.setVisible(false);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	Intent intent;
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_register):
    			intent = new Intent(getActivity(), RegisterActivity.class);
    			startActivity(intent);
    			
    			return true;
    		
    		case(R.id.menu_action_add_item):
    			intent = new Intent(getActivity(), NewItemActivity.class);
    			startActivity(intent);
    			
    			return true;
    			
    		case(R.id.menu_action_login):
    			intent = new Intent(getActivity(), LoginActivity.class);
    			startActivity(intent);
    			
    			return true;
    			
    		case(R.id.menu_action_list_refresh):
    			populateItems();
    		
    			return true;
    			
    		default: return false;
    	}
    }
    
    private void populateItems() {
    	if (refreshItem != null)
    		refreshItem.setActionView(R.layout.refresh_spinner);
    	
    	ParseQuery listQuery = new ParseQuery("listItems");
    	// Ideally we'd use CACHE_THEN_NETWORK here, but we'd then want to only change the
    	// ActionView on the second callback, because CACHE_THEN_NETWORK calls FindCallback() twice
    	listQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		listQuery.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> itemList, ParseException e) {
				if (refreshItem != null)
					refreshItem.setActionView(null);
				
				if (e == null) {
					listItems = new ArrayList<HashMap<String, String>>(2);
					
					HashMap<String, String> map;
			    	for (ParseObject object : itemList) {
			    		map = new HashMap<String, String>();
			    		map.put("title", object.getString("title"));
			    		
			    		listItems.add(map);
			    	}
			    	
			    	String[] from = {"title"};
			    	int[] to = {android.R.id.text1};
			    	
			    	SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems, android.R.layout.simple_list_item_1, from, to);
			    	setListAdapter(adapter);
				} else {
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
			
		});
    }
}
