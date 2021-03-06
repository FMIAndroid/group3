package com.fmi.android.course.project.utils;

import java.util.ArrayList;
import java.util.List;

import com.fmi.android.course.project.models.User;

public class UsersStorage {

	private static UsersStorage sInstance;

	private List<User> mUsers;

	private UsersStorage() {
		mUsers = new ArrayList<User>();
	}

	public static UsersStorage getInstance() {
		if (sInstance == null) {
			sInstance = new UsersStorage();
		}

		return sInstance;
	}

	public void saveUser(User user) {
		mUsers.add(user);
	}

	public List<User> getUsers() {
		return mUsers;
	}
	
}
