package com.android.course.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BroadCastingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broad_casting);

		// Every time a button is clicked, we want to broadcast a notification.
		Button sendBroadCast = (Button)findViewById(R.id.send_brodcast);
		sendBroadCast.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sendMessage();
					}
				});

	}

	// Send an Intent with an action named "custom-event-name". The Intent sent
	// should
	// be received by the ReceiverActivity.
	private void sendMessage() {
		Log.d("sender", "Broadcasting message");
		Intent intent = new Intent("com.android.course.RECIEVE");
		// You can also include some extra data.
		intent.putExtra("message", "This is my message!");
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
}
