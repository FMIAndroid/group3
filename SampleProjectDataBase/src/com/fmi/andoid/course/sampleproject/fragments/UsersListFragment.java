package com.fmi.andoid.course.sampleproject.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fmi.andoid.coruse.sampleproject.db.R;
import com.fmi.andoid.course.sampleproject.activities.MainActivity;
import com.fmi.andoid.course.sampleproject.activities.RegisterActivity;
import com.fmi.andoid.course.sampleproject.adapters.CustomAdpter;
import com.fmi.android.course.sampleproject.database.DBManager;
import com.fmi.android.course.sampleproject.utils.User;

public class UsersListFragment extends Fragment {

	private ListView usersListView;
	private ArrayList<User> users;
	private Button addMore;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_list,
				container, false);

		usersListView = (ListView) rootView.findViewById(R.id.usersList);
		addMore = (Button) rootView.findViewById(R.id.add_more);

		//Set onClickListener to Add More Users button
		addMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startRegisterActivity();

			}

		});


		return rootView;
	}

	@Override
	public void onResume(){
		super.onResume();
		//Get users from Data Base
		try {
			users = DBManager.getInstance().getAllUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// users = (ArrayList<User>) UsersStorage.getInstance()
		// .getUsers();
		
		Log.d("DEBUG", "users : " + users.size());
		//We don't have any users yet 
		if (users == null) {
			Toast.makeText(getActivity(), "Ooops, there are no users yet!",
					Toast.LENGTH_LONG).show();
		} else {
			Log.d("DEBUG", "users : " + users.size());
			// We have users
			CustomAdpter mAdapter = new CustomAdpter(getActivity(),
					R.layout.users_list_item_layout, users);
			usersListView.setAdapter(mAdapter);

			usersListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							Fragment detailsFragment = DetailsFragment
									.getInstance(users.get(position));
							((MainActivity) getActivity())
									.replaceFragments(detailsFragment);

						}
					});
		}
	}
	private void startRegisterActivity() {
		Intent register = new Intent(getActivity(), RegisterActivity.class);
		startActivity(register);

	}
}
