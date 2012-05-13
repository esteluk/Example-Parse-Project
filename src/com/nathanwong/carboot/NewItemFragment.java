package com.nathanwong.carboot;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewItemFragment extends Fragment {
	
	@Override
	public View onCreateView (LayoutInflater inflater,
							  ViewGroup container,
							  Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		// Create the UI
		return inflater.inflate(R.layout.fragment_newitem, container, false);
	}
}
