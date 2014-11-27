package com.fmi.android.course.project.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmi.android.course.project.R;
import com.fmi.android.course.project.models.User;
import com.fmi.android.course.project.utils.Constanst;
import com.fmi.android.course.project.utils.UsersStorage;

public class DetailsActivity extends Activity {

	private ImageView userImage;
	private TextView userName;
	private TextView userEmail;
	private Button userListButton ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		userName = (TextView) findViewById(R.id.user_name);
		userEmail = (TextView) findViewById(R.id.email);
		userImage = (ImageView) findViewById(R.id.user_image);
		userListButton = (Button) findViewById(R.id.view_list);

		populateUserData();
		
		
		userListButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent usersListIntent = new Intent(DetailsActivity.this, ListActivity.class);
				startActivity(usersListIntent);
				
			}
		});
	}

	private void populateUserData() {
		SharedPreferences settings = getSharedPreferences(Constanst.PREFS_NAME,
				MODE_PRIVATE);
		String  imagePath = settings.getString(Constanst.IMAGE_PATH, null);
		String 	name = settings.getString(Constanst.NAME, "");
		String email = settings.getString(Constanst.EMAIL, "");
		String password = settings.getString(Constanst.PASSWORD, "");
		
		userImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
		userName.setText(name);
		userEmail.setText(email);
		
		/* Save our user into out storage List */
		User user = new User(name, email, password, imagePath);
		
		UsersStorage.getInstance().saveUser(user);
	}
}
