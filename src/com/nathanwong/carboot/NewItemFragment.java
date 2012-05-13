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
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class NewItemFragment extends Fragment {
	
	private OnNewItemSavedListener onNewItemSavedListener;
	private static String TAG = "NewItemFragment";
	
	@Override
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		
		try {
			onNewItemSavedListener = (OnNewItemSavedListener) activity;			
		} catch (ClassCastException e) {
			throw new ClassCastException (activity.toString() +
					"must implement OnNewItemSavedListener");
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
		super.onCreateView(inflater, container, savedInstanceState);
		
		// Create the UI
		return inflater.inflate(R.layout.fragment_newitem, container, false);
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	
    	inflater.inflate(R.menu.new_item, menu);
	}
    
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	
    	switch(item.getItemId()) {
    		case(R.id.menu_action_newitem_accept):
    			Log.d(TAG, "Pressed tick!");
    			saveObject();
    			
    			
    			return true;
    		
    		default: return false;
    	}
    }
    
    private void saveObject () {
    	EditText title = (EditText) getView().findViewById(R.id.fragment_newitem_edittext_title);
    	
    	ParseObject item = new ParseObject("listItems");
		item.put("title", title.getText().toString());
		item.saveInBackground(new SaveCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Successfully saved. Report to parent.
					onNewItemSavedListener.onNewItemSaved();
				}
				else {
					Toast.makeText(getActivity(), getString(R.string.newitem_form_submit_failure), Toast.LENGTH_LONG);
					Log.d(TAG, "Error: " + e.getMessage());
				}
			}
		});
    }
    
    public interface OnNewItemSavedListener {
    	public void onNewItemSaved();
    }
}
