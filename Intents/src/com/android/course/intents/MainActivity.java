package com.android.course.intents;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private final static String EMAIL = "gva@gmail.com";
	private final static String PASSWORD = "123456";
	private final static int LOGIN = 1;
	public final static int DESCRIPTION = 2;
	private final static int PICK_CONTACT = 3;
	private final static int SELECT_PICTURE = 4;
	private static final int REQUEST_IMAGE_CAPTURE = 5;
	private static final int SEND_EMAIL = 6;

	private String userEmail;
	private String userPassword;

	private TextView phone;
	private TextView description;
	private ImageView userImage;

	private IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get views instances
		Button change_phone = (Button) findViewById(R.id.change_phone_number);
		Button change_description = (Button) findViewById(R.id.change_description);
		Button send_email = (Button) findViewById(R.id.send_email);
		Button start_animation = (Button) findViewById(R.id.open_animation);
		Button broadcast = (Button) findViewById(R.id.broadcast);

		userImage = (ImageView) findViewById(R.id.user_image);
		phone = (TextView) findViewById(R.id.user_phone);
		description = (TextView) findViewById(R.id.description);

		// Set MainActivity as onClickListener to the buttons and the user image
		change_description.setOnClickListener(this);
		change_phone.setOnClickListener(this);
		userImage.setOnClickListener(this);
		send_email.setOnClickListener(this);
		start_animation.setOnClickListener(this);
		broadcast.setOnClickListener(this);

		// Boolean isLogged = checkCredentials();
		// if (!isLogged) {
		// startLogInForm();
		// }

		// the intent filter will be action =
		// "com.android.course.RECIEVE"
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		filter = new IntentFilter("com.android.course.RECIEVE");
		// register the receiver:
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);

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
			// Return from EditDescriptionActivity
			case DESCRIPTION:
				if (data.hasExtra("description")) {
					String desc = data.getStringExtra("description");
					description.setText(desc);
				}
				break;
			// Return from Contact list
			case PICK_CONTACT:

				Uri contactData = data.getData();
				getPhoneFromUri(contactData);

				break;
			case SELECT_PICTURE:
				Uri selectedImageUri = data.getData();

				getImageFromUri(selectedImageUri);

				break;
			case REQUEST_IMAGE_CAPTURE:
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				userImage.setImageBitmap(imageBitmap);

				// Set animation for the user profile image
				Animation animBounce = AnimationUtils.loadAnimation(
						MainActivity.this, R.anim.bounce_animation);

				userImage.startAnimation(animBounce);
				break;
			case SEND_EMAIL:

				break;
			default:
				break;
			}

		}
	}

	@Override
	public void onClick(View v) {
		// Get view id
		int id = v.getId();
		// Determine witch view is pressed and do the right thing
		switch (id) {
		case R.id.change_description:
			Intent desctiptionIntent = new Intent(this,
					EditDescriptionActivity.class);
			startActivityForResult(desctiptionIntent, DESCRIPTION);
			break;
		case R.id.change_phone_number:
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT);
			break;
		case R.id.user_image:
			// Intent intentImage = new Intent();
			// intentImage.setType("image/*");
			// intentImage.setAction(Intent.ACTION_GET_CONTENT);
			// startActivityForResult(
			// Intent.createChooser(intentImage, "Select Picture"),
			// SELECT_PICTURE);

			dispatchTakePictureIntent();
			break;
		case R.id.send_email:
			dispachSendEmailIntent();
			break;
		case R.id.open_animation:
			Intent animationIntent = new Intent();
			animationIntent
					.setAction("com.android.course.intents.ANIMATION_ACTION");
			startActivity(animationIntent);
			break;
		case R.id.broadcast:
			Intent broadcastIntent = new Intent(this,
					BroadCastingActivity.class);
			startActivity(broadcastIntent);
			break;
		default:
			break;
		}

	}

	/**
	 * Method witch checks if the user is logged in
	 * 
	 * @return boolean
	 */
	private boolean checkCredentials() {
		if (userEmail != null && userPassword != null
				&& userEmail.equals(EMAIL) && userPassword.equals(PASSWORD)) {
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
	 * Method witch retrieves phone number from contact
	 * 
	 * @param Uri
	 *            contactData
	 */

	private void getPhoneFromUri(Uri contactData) {
		Cursor c = getContentResolver().query(contactData, null, null, null,
				null);
		if (c.moveToFirst()) {

			String id = c.getString(c
					.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

			String hasPhone = c
					.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

			if (hasPhone.equalsIgnoreCase("1")) {
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + id, null, null);
				phones.moveToFirst();
				String cNumber = phones.getString(phones
						.getColumnIndex("data1"));

				phone.setText(cNumber);
			}
			// String name = c
			// .getString(c
			// .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}
	}

	/**
	 * Method to get decode image from uri
	 * 
	 * @param selectedImageUri
	 */
	private void getImageFromUri(Uri selectedImageUri) {
		ParcelFileDescriptor parcelFileDescriptor;
		try {
			parcelFileDescriptor = getContentResolver().openFileDescriptor(
					selectedImageUri, "r");
			FileDescriptor fileDescriptor = parcelFileDescriptor
					.getFileDescriptor();
			Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
			parcelFileDescriptor.close();
			userImage.setImageBitmap(image);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Launch intent for the hardware camera
	 */
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	/**
	 * Start Intent to open Email app and send email
	 */
	private void dispachSendEmailIntent() {
		// Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("text/html");
		// intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
		// intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
		// intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
		//
		// startActivity(Intent.createChooser(intent, "Send Email"));

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", "abc@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
		startActivityForResult(
				Intent.createChooser(emailIntent, "Send email..."), SEND_EMAIL);
	}

	@Override
	protected void onDestroy() {
		// Unregister since the activity is about to be closed.
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

	// My BroadcastReciever
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get extra data included in the Intent
			String message = intent.getStringExtra("message");
			Log.d("receiver", "Got message: " + message);
		}
	};
}
