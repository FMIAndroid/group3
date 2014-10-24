package com.fmi.android.course.userinterfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button login = (Button) findViewById(R.id.login_button);
		login.setText(getResources().getString(R.string.hello_world));

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				login();

			}

		});
	}

	private void login() {
		Intent intent = new Intent(MainActivity.this, SecondActivity.class);
		this.startActivity(intent);

	}
}
