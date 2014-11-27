package com.fmi.android.course.project.activities;

import java.util.ArrayList;

import com.fmi.android.course.project.R;
import com.fmi.android.course.project.adapters.CustomAdpter;
import com.fmi.android.course.project.models.User;
import com.fmi.android.course.project.utils.UsersStorage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends Activity {



	private ListView usersListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		usersListView = (ListView) findViewById(R.id.usersList);
		
		ArrayList<User> users = (ArrayList<User>) UsersStorage.getInstance().getUsers();
		CustomAdpter mAdapter = new CustomAdpter(this, R.layout.users_list_item_layout, users);
		usersListView.setAdapter(mAdapter);
		
	}

	protected void onResume() {
		super.onResume();
		
	}

	
	
}
