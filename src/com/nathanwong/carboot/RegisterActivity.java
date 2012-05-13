package com.nathanwong.carboot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class RegisterActivity extends Activity implements RegisterFragment.OnRegisteredListener {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	}

	@Override
	public void onRegistered(String message) {
		Toast.makeText(this, getString(R.string.register_form_success), Toast.LENGTH_LONG).show();
		finish();
	}
}
