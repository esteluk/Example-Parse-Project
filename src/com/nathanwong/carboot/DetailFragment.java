package com.nathanwong.carboot;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {
	@Override
	public View onCreateView (LayoutInflater inflater,
			  ViewGroup container,
			  Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		return inflater.inflate(R.layout.fragment_detail, container, false);
	}
}
