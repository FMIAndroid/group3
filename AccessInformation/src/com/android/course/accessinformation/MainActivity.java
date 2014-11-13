package com.android.course.accessinformation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static String PASSWORD = "password";
	private final static String EMAIL = "email";
	private final static String STORAGE = "storage";
	private final static int LOGIN = 1;

	private String userEmail;
	private String userPassword;

	private EditText editText;
	private TextView fileContents;
	private TextView status_tv;
	private RadioButton external_radio;
	private RadioButton internal_radio;
	private RadioButton row_radio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.editText);
		fileContents = (TextView) findViewById(R.id.showFileContent);
		external_radio = (RadioButton) findViewById(R.id.external_radio);
		internal_radio = (RadioButton) findViewById(R.id.internal_radio);
		row_radio = (RadioButton) findViewById(R.id.row_radio);
		status_tv = (TextView) findViewById(R.id.status_tv);

		saveCredentials();
	}

	protected void onResume() {
		super.onResume();
		if (!checkCredentials()) {
			startLogInForm();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			switch (requestCode) {
			// Return from LoginActivity
			case LOGIN:
				userEmail = data.getStringExtra("email");
				userPassword = data.getStringExtra("password");
				// Check again credentials
				if (checkCredentials()) {
					Toast.makeText(this, "You have logged in succesfully!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, "Wrong credentials!",
							Toast.LENGTH_SHORT).show();
					startLogInForm();
				}
				break;
			}
		}
	}

	/**
	 * Save credentials in SharePrefs
	 */

	private void saveCredentials() {
		SharedPreferences settings = getSharedPreferences("mySharedPreffs",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PASSWORD, "12345");
		editor.putString(EMAIL, "gva@gmail.com");
		editor.commit();
	}

	/**
	 * Method witch checks if the user is logged in
	 * 
	 * @return boolean
	 */
	private boolean checkCredentials() {
		SharedPreferences settings = getSharedPreferences("mySharedPreffs",
				MODE_PRIVATE);

		if (userEmail != null && userPassword != null
				&& userEmail.equals(settings.getString(EMAIL, null))
				&& userPassword.equals(settings.getString(PASSWORD, null))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method witch stars an Intent for LoginActivity
	 */
	private void startLogInForm() {
		Intent i = new Intent(MainActivity.this, LoginActivity.class);
		startActivityForResult(i, LOGIN);

	}

	/**
	 * Save on storage
	 * 
	 * @param v
	 */
	public void onSaveClick(View v) {

		SharedPreferences settings = getSharedPreferences("mySharedPreffs",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		String content = editText.getText().toString();

		if (external_radio.isChecked()) {
			editor.putString(STORAGE, "extenal");
			if (isExternalStorageWritable()) {
				writeExternalStorage(this, "myData.txt", content);
			}
		}

		if (internal_radio.isChecked()) {
			editor.putString(STORAGE, "internal");
			writeInternalStorage(content);
		}

		editor.commit();

	}

	/**
	 * Read file content
	 * 
	 * @param v
	 */
	public void onLoadClick(View v) {
		SharedPreferences settings = getSharedPreferences("mySharedPreffs",
				MODE_PRIVATE);
		String storage = settings.getString(STORAGE, null);
		if (storage != null && storage.equals("extenal")) {
			if (isExternalStorageReadable()) {
				readFileFromExternalStorage();
			}
		} else if (storage != null && storage.equals("internal")) {
			readInternalStorage();
		}
		
		if(row_radio.isChecked()){
			readRaw();
		}

	}

	/**
	 * Write file in internal storage
	 * 
	 * @param content
	 */
	private void writeInternalStorage(String content) {
		String filename = "myfile.txt";
		String string = content;
		FileOutputStream outputStream = null;
		try {
			outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private void readInternalStorage() {

		FileInputStream fin = null;
		try {
			fin = openFileInput("myfile.txt");

			int c;
			String temp = "";
			while ((c = fin.read()) != -1) {
				temp = temp + Character.toString((char) c);
			}
			fileContents.setText(temp);

			// string temp contains all the data of the file.
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;

	}

	/* Checks if external storage is available to at least read */
	private boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Write file to external storage
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	private void writeExternalStorage(Context context, String filename,
			String content) {
		status_tv.setText("");
		// Get the directory for the app's private file directory.
		File root = android.os.Environment.getExternalStorageDirectory();
		status_tv.append("\nExternal file system root: " + root);

		File dir = new File(root.getAbsolutePath() + "/android-course");
		dir.mkdirs();
		File file = new File(dir, filename);

		try {
			FileOutputStream f = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(f);
			pw.println(content);
			pw.flush();
			pw.close();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("DEBUG",
					"******* File not found. Did you"
							+ " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
		} catch (IOException e) {
			e.printStackTrace();
		}
		status_tv.append("\n\nFile written to " + file);

	}

	/**
	 * Read file from external storage
	 * 
	 */
	private void readFileFromExternalStorage() {
		status_tv.setText("");

		File root = android.os.Environment.getExternalStorageDirectory();
		status_tv.append("\nExternal file system root: " + root);

		File dir = new File(root.getAbsolutePath() + "/android-course");

		File file = new File(dir, "myData.txt");
		FileInputStream is;
		try {
			is = new FileInputStream(file);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is
																// buffer
																// size

			String test;
			fileContents.setText("");
			while (true) {
				test = br.readLine();
				// readLine() returns null if no more lines in the file
				if (test == null)
					break;
				fileContents.append("\n" + "    " + test);
			}
			isr.close();
			is.close();
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		status_tv.append("\n\nThat is all");
	}

	/**
	 * Method to read in a text file placed in the res/raw directory of the
	 * application. The method reads in all lines of the file sequentially.
	 */

	private void readRaw() {
		status_tv.setText("");

		status_tv.append("\nData read from res/raw/textfile.txt:");
		InputStream is = this.getResources().openRawResource(R.raw.textfile);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is buffer
															// size

		// More efficient (less readable) implementation of above is the
		// composite expression
		/*
		 * BufferedReader br = new BufferedReader(new InputStreamReader(
		 * this.getResources().openRawResource(R.raw.textfile)), 8192);
		 */

		fileContents.setText("");
		try {
			String test;
			while (true) {
				test = br.readLine();
				// readLine() returns null if no more lines in the file
				if (test == null)
					break;
				fileContents.append("\n" + "    " + test);
			}
			isr.close();
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// status_tv.append("\n\nThat is all");
	}
}
