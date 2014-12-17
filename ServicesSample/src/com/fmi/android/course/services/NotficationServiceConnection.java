package com.fmi.android.course.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class NotficationServiceConnection implements ServiceConnection {

	private NotificationService.LocalBinder binder;

	public NotificationService getService() {
		return binder.getService();
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		Log.d("DEBUG", "onServiceConnected");
		
		binder = (NotificationService.LocalBinder) service;
		getService().buidNotification("123", "hi");
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		Log.d("DEBUG", "onServiceDisconnected");
		binder = null;

	}
}
