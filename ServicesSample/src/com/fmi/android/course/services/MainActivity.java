package com.fmi.android.course.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private NotficationServiceConnection notificationServiceConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		Log.d("DEBUG", "onStart()");
		super.onStart();
		notificationServiceConnection = new NotficationServiceConnection();
		Intent intent = new Intent(MainActivity.this, NotificationService.class);
		//Log.d("DEBUG", "" + startService(intent));
		bindService(intent, notificationServiceConnection, BIND_AUTO_CREATE);
		
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(notificationServiceConnection);
		notificationServiceConnection = null;
		
	}

}
