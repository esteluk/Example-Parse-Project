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

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterFragment extends Fragment {
	
	EditText emailField;
	EditText passwordField;
	ProgressBar spinner;
	
	private OnRegisteredListener onRegisteredListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			onRegisteredListener = (OnRegisteredListener) activity;			
		} catch (ClassCastException e) {
			throw new ClassCastException (activity.toString() +
					"must implement OnRegisteredListener");
		}
	}
	
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		Parse.initialize(getActivity(), getString(R.string.applicationId), getString(R.string.clientKey));
	}
	@Override
	public View onCreateView (LayoutInflater inflater,
							  ViewGroup container,
							  Bundle savedInstanceState) {
		// Create the fragment's UI
		return inflater.inflate(R.layout.fragment_register, container, false);
	}
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		emailField = (EditText) getView().findViewById(R.id.edittext_email);
		passwordField = (EditText) getView().findViewById(R.id.edittext_password);
		spinner = (ProgressBar) getView().findViewById(R.id.register_spinner);
		
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.register, menu);
	}
    
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_register_accept):
    			spinner.setVisibility(View.VISIBLE);
    			register(emailField.getText().toString(), passwordField.getText().toString());
    			
    			return true;

    			
    		default: return false;
    	}
    }
    
    private void register (String email, String password) {
    	if (email.equals("")) {
			setErrorText(getString(R.string.register_form_error_email_required));
		} else if (password.equals("")) {
			setErrorText(getString(R.string.register_form_error_password_required));
		} else {
			ParseUser user = ParseUser.getCurrentUser();
			user.setEmail(email);
			user.setUsername(email);
			user.setPassword(password);
			
			user.signUpInBackground(new SignUpCallback() {
				public void done(ParseException e) {
					// Hide the spinner!
					spinner.setVisibility(View.INVISIBLE);
					
					if (e == null) {
						onRegisteredListener.onRegistered(getString(R.string.register_form_success));
					} else {
						if (e.getCode() == ParseException.EMAIL_MISSING) {
							setErrorText(getString(R.string.register_form_error_email_required));
						}
						else if (e.getCode() == ParseException.EMAIL_TAKEN) {
							setErrorText(getString(R.string.register_form_error_email_taken));
						}
						else if (e.getCode() == ParseException.INVALID_EMAIL_ADDRESS) {
							setErrorText(getString(R.string.register_form_error_email_invalid));
						}
						else if (e.getCode() == ParseException.USERNAME_MISSING) {
							setErrorText(getString(R.string.register_form_error_email_required));
						}
						else if (e.getCode() == ParseException.USERNAME_TAKEN) {
							setErrorText(getString(R.string.register_form_error_email_taken));
						}
						else if (e.getCode() == ParseException.PASSWORD_MISSING) {
							setErrorText(getString(R.string.register_form_error_password_required));
						} else {
							setErrorText(getString(R.string.register_form_error_unknwon));
							Log.d("Register", "Unknown error: " + e.getMessage());
						}
					}
				}
			});
		}
    }
	
	public interface OnRegisteredListener {
		public void onRegistered(String message);
	}
	
	public void setErrorText(String text) {
		final TextView error = (TextView) getView().findViewById(R.id.register_form_error_box);
		error.setVisibility(View.VISIBLE);
		error.setText(text);
	}
}
