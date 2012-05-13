package com.nathanwong.carboot;

import android.app.Activity;
import android.os.Bundle;

public class NewItemActivity extends Activity implements NewItemFragment.OnNewItemSavedListener {
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newitem);
	}

	@Override
	public void onNewItemSaved() {
		finish();
	}
}