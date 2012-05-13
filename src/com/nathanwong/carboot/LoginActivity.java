package com.nathanwong.carboot;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class LoginActivity extends Activity implements LoginFragment.OnLoggedInListener {
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
	}

	@Override
	public void onLoggedIn() {
		Toast.makeText(this, getString(R.string.login_form_success), Toast.LENGTH_LONG);
		finish();
	}
}
