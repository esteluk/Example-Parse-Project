package com.nathanwong.carboot;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginFragment extends Fragment {
	
	EditText emailField;
	EditText passwordField;
	ProgressBar spinner;
	TextView errorBox;
	
	OnLoggedInListener onLoggedInListener;
	
	private static String TAG = "LoginFragment";
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			onLoggedInListener = (OnLoggedInListener) activity;			
		} catch (ClassCastException e) {
			throw new ClassCastException (activity.toString() +
					"must implement OnLoggedInListener");
		}
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater,
							  ViewGroup container,
							  Bundle savedInstanceState) {
		// Create the fragment's UI
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		emailField = (EditText) getView().findViewById(R.id.login_form_edittext_email);
		passwordField = (EditText) getView().findViewById(R.id.login_form_edittext_password);
		spinner = (ProgressBar) getView().findViewById(R.id.login_form_spinner);
		errorBox = (TextView) getView().findViewById(R.id.login_form_error_box);
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.login, menu);
	}
    
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_login_accept):
    			login();
    			
    			return true;

    			
    		default: return false;
    	}
    }
    
    private void login() {
    	spinner.setVisibility(View.VISIBLE);
    	
    	String email = emailField.getText().toString();
    	String password = passwordField.getText().toString();
    	
    	ParseUser.logInInBackground(email, password, new LogInCallback() {
    		public void done (ParseUser user, ParseException e) {
				spinner.setVisibility(View.INVISIBLE);
				
    			if (e == null && user != null) {
    				// Logged in!
    				onLoggedInListener.onLoggedIn();
    			} else {
    				// Error!
    				errorBox.setVisibility(View.VISIBLE);
    				errorBox.setText("Incorrect username or password!");
    				Log.d(TAG, e.getMessage());
    			}
    		}
    	});
    }
    
    public interface OnLoggedInListener {
    	public void onLoggedIn();
    }
}
