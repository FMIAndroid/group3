package com.fmi.andoid.coruse.sampleproject.fragments;

import java.util.ArrayList;

import utils.User;
import utils.UsersStorage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fmi.andoid.coruse.sampleproject.R;
import com.fmi.andoid.coruse.sampleproject.activities.MainActivity;
import com.fmi.andoid.coruse.sampleproject.adapters.CustomAdpter;

public class UsersListFragment extends Fragment {

	private ListView usersListView;
	private ArrayList<User> users;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_list,
				container, false);

		usersListView = (ListView) rootView.findViewById(R.id.usersList);

		 users = (ArrayList<User>) UsersStorage.getInstance()
				.getUsers();
		CustomAdpter mAdapter = new CustomAdpter(getActivity(),
				R.layout.users_list_item_layout, users);
		usersListView.setAdapter(mAdapter);
		
		
		usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Fragment detailsFragment = DetailsFragment.getInstance(users.get(position));
				((MainActivity) getActivity()).replaceFragments(detailsFragment);
				
			}
		});
		return rootView;
	}

}
