package com.fmi.andoid.coruse.sampleproject.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fmi.andoid.coruse.sampleproject.R;
import com.fmi.andoid.coruse.sampleproject.fragments.UsersListFragment;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final UsersListFragment fragment = new UsersListFragment();
		
		replaceFragments(fragment);
	
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

		}

	}

	public void replaceFragments(Fragment fragment) {
		final FragmentManager manager = getSupportFragmentManager();

		final FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.details_fragment_container, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
