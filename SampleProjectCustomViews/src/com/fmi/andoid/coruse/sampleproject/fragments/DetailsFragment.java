package com.fmi.andoid.coruse.sampleproject.fragments;

import utils.User;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmi.andoid.coruse.sampleproject.R;

public class DetailsFragment extends Fragment {

	private ImageView userImage;
	private TextView userName;
	private TextView userEmail;
	private Button userListButton;

	private final static String USER = "user";

	public static DetailsFragment getInstance(User user) {
		DetailsFragment fragment = new DetailsFragment();
		Bundle args = new Bundle();
		args.putSerializable(USER, user);
		fragment.setArguments(args);
		return fragment;

	}

	// Default constructor
	public DetailsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_details,
				container, false);

		userName = (TextView) rootView.findViewById(R.id.user_name);
		userEmail = (TextView) rootView.findViewById(R.id.email);
		userImage = (ImageView) rootView.findViewById(R.id.user_image);

		populateUserData();

		return rootView;
	}

	private void populateUserData() {

		User mUser = (User) getArguments().getSerializable(USER);
		String imagePath = mUser.getImagePath();
		String name = mUser.getName();
		String email = mUser.getEmail();
		String password = mUser.getPassword();

		userImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
		userName.setText(name);
		userEmail.setText(email);

	}

}
