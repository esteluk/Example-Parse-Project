package com.nathanwong.carboot;

import android.app.Activity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class CarBootInventoryActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Parse.initialize(this, getString(R.string.applicationId), getString(R.string.clientKey));
        ParseUser.enableAutomaticUser();
        ParseACL.setDefaultACL(new ParseACL(), true);
    }
    
}