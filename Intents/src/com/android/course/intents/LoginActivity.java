package com.android.course.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
	}
	
	public void onLoginClick(View v){
		EditText emailEditText = (EditText)findViewById(R.id.email);
		EditText passwordEditText = (EditText) findViewById(R.id.password);
		
		String email = emailEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		Intent returnIntent= new Intent(this, MainActivity.class);
		returnIntent.putExtra("email", email);
		returnIntent.putExtra("password", password);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
