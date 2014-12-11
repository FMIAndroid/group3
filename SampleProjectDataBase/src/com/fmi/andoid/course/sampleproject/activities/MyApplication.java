package com.fmi.andoid.course.sampleproject.activities;

import com.fmi.android.course.sampleproject.database.DBConnector;

import android.app.Application;
import android.widget.Toast;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		initDatabase();
	}

	/**
	 * * Initialize the database.
	 */
	private void initDatabase() {
		try {
			// Initialize DB instance
			
			DBConnector.initialize(getApplicationContext());
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"CAN'T INITIALIZE DATABASE", Toast.LENGTH_LONG).show();

		}
	}
	
	
}
