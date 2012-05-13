package com.nathanwong.carboot;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class ItemListFragment extends ListFragment {
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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
}
