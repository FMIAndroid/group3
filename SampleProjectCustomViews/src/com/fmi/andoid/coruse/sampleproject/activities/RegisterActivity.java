package com.fmi.andoid.coruse.sampleproject.activities;

import java.io.File;
import java.io.IOException;

import utils.Constanst;
import utils.User;
import utils.UsersStorage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fmi.andoid.coruse.sampleproject.R;


public class RegisterActivity extends Activity {

	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private ImageView userImage;
	private EditText userName;
	private EditText userPassword;
	private EditText userConfirmPassword;
	private EditText userEmail;
	private String mPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		userName = (EditText) findViewById(R.id.user_name);
		userPassword = (EditText) findViewById(R.id.password);
		userConfirmPassword = (EditText) findViewById(R.id.confirm_password);
		userEmail = (EditText) findViewById(R.id.email);
		userImage = (ImageView) findViewById(R.id.user_image);

		// Set onCLickListener
		userImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dispatchTakePictureIntent();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {

			switch (requestCode) {
			// Return from LoginActivity
			case REQUEST_IMAGE_CAPTURE:

				userImage.setImageBitmap(BitmapFactory.decodeFile(mPhotoPath));
				break;
			}
		}

	}

	public void onRegisterClick(View v) {


		if (saveCredentials()) {
			Intent detailsIntent = new Intent(this, MainActivity.class);
			startActivity(detailsIntent);
			
		
		}
	}

	/**
	 * Save credentials in SharePrefs
	 */

	private Boolean saveCredentials() {
		SharedPreferences settings = getSharedPreferences(Constanst.PREFS_NAME,
				MODE_PRIVATE);

		String password  = userPassword.getText().toString();
		String confirmPass = userConfirmPassword.getText().toString();
		String name = userName.getText().toString();
		String email = userEmail.getText().toString();
		
		if(!password.equals(confirmPass)){
			Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_LONG).show();
			return false;
		}
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Constanst.NAME, name);
		editor.putString(Constanst.PASSWORD, password);
		editor.putString(Constanst.EMAIL, email);
		editor.putString(Constanst.IMAGE_PATH, mPhotoPath);
		editor.commit();
		
		/* Save our user into out storage List */
		User user = new User(name, email, password, mPhotoPath);
		
		UsersStorage.getInstance().saveUser(user);
		return true;
	}

	/**
	 * Launch intent for the hardware camera
	 */
	private void dispatchTakePictureIntent() {

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imageFile = createImageFile();
		if (imageFile == null) {
			Log.e("ERROR", "Temp file is NULL!");
			return;
		}
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

		startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

	}

	private File createImageFile() {
	
		if (!isExternalStorageWritable()) {
			return null;
		}

		String fileName = "IMG_PROFILE";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image;
		try {
			image = File.createTempFile(fileName, ".png", storageDir);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		mPhotoPath = image.getAbsolutePath();

		return image;
	}

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;

	}

	


}
