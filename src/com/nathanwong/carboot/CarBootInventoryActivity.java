package com.nathanwong.carboot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class CarBootInventoryActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Parse.initialize(this, getString(R.string.applicationId), getString(R.string.clientKey));
        ParseUser.enableAutomaticUser();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.home, menu);
    	
    	return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    	
    	if (! ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
	    	MenuItem item = menu.findItem(R.id.menu_action_register);
	    	item.setEnabled(false);
	    	item.setVisible(false);
    	}

    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_register):
    			Intent intent = new Intent(CarBootInventoryActivity.this, RegisterActivity.class);
    			startActivity(intent);
    			
    			return true;
    		
    		default: return false;
    	}
    }
}