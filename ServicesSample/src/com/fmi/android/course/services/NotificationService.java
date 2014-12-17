package com.fmi.android.course.services;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationService extends Service {

		public class LocalBinder extends Binder {
			public NotificationService getService() {
				return NotificationService.this;
			}
		}
		
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			return START_NOT_STICKY;
		}
		
		@Override
		public IBinder
		onBind(Intent intent) {
			return new LocalBinder();
		}
		
		public void buidNotification(String title, String message) {
			NotificationCompat.Builder mBuilder =
				    new NotificationCompat.Builder(this)
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setContentTitle("My notification")
				    .setContentText("Hello World!");
			
			Notification notification = mBuilder.build();
			// Sets an ID for the notification
			int mNotificationId = 001;
			// Gets an instance of the NotificationManager service
			NotificationManager mNotifyMgr = 
			        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			// Builds the notification and issues it.
			mNotifyMgr.notify(mNotificationId,notification);
			Log.d("DEBUG", "buidNotification: " + notification);

		}

	
}
