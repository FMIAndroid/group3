package com.android.course.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_description);
	}

	@Override
	public void onBackPressed() {
		EditText edit = (EditText) findViewById(R.id.edit_description);
		String desctiption = edit.getText().toString();
		Intent back = new Intent(this, MainActivity.class);
		back.putExtra("description", desctiption);
		setResult(RESULT_OK, back);
		
		super.onBackPressed();
	}
	
	
}
