package com.fmi.androidcourse2014;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String SAVE_STATE_KEY = "STATE";
	private static final String SAVE_VISITS = "VISITS";

	private static final String TAG = MainActivity.class.getSimpleName();
	private TextView textView;
	private Button button;
	private EditText edittext;
	private int count = 1;
	private int textColor;

	private boolean colorflag = false;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.text_view);
		button = (Button) findViewById(R.id.button);
		edittext = (EditText) findViewById(R.id.edittext);

		Log.d(TAG, "onCreate()");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, "onRestoreInstanceState()");
		//textColor = savedInstanceState.getInt(SAVE_STATE_KEY);
		//count = savedInstanceState.getInt(SAVE_VISITS);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		textView.setText("Number of visits:" + count);
		count++;
		// Check for the color
		if (textColor == Color.BLUE) {
			textView.setTextColor(textColor);
			colorflag = true;
		} else if (textColor == Color.RED) {
			textView.setTextColor(textColor);
			colorflag = false;
		}
		Log.d(TAG, "onResume()");
		//Log.i(TAG, "count: " + count);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		Log.d(TAG, "onPause()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState()");
		//outState.putInt(SAVE_STATE_KEY, textColor);
		//outState.putInt(SAVE_VISITS, count-1);

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy()");
	}

	public void onButtonClick(View v) {
		Intent i = new Intent(MainActivity.this, SecondActivity.class);
		this.startActivity(i);

	}

	public void onButtonChangeColorClick(View v) {
		if (colorflag) {
			textView.setTextColor(Color.RED);
			textColor = Color.RED;
			colorflag = false;
		} else {
			textView.setTextColor(Color.BLUE);
			textColor = Color.BLUE;
			colorflag = true;
		}

	}
}
