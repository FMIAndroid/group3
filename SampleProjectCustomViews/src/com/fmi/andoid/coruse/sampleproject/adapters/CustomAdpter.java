package com.fmi.andoid.coruse.sampleproject.adapters;

import java.util.ArrayList;
import java.util.List;

import utils.User;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmi.andoid.coruse.sampleproject.R;


public class CustomAdpter extends ArrayAdapter<User> {
	private ArrayList<User> mUsers;
	private int mResource;

	public CustomAdpter(Context context, int resource, List<User> users) {
		super(context, resource, users);
		this.mUsers = (ArrayList<User>) users;
		this.mResource = resource;

	}

	@Override
	public int getCount() {

		return mUsers.size();
	}

	@Override
	public User getItem(int position) {

		return mUsers.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// assign the view we are converting to a local variable
		View v = convertView;
		ViewHolder holder;
		User user = mUsers.get(position);
		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(mResource, null);

			holder.userImage = (ImageView) v.findViewById(R.id.image);
			holder.userName = (TextView) v.findViewById(R.id.name);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}

		// set date toholder fields

		holder.userName.setText(user.getName());
		holder.userImage.setImageBitmap(BitmapFactory.decodeFile(user
				.getImagePath()));
		
		
		return v;
	}

	private static class ViewHolder {
		public TextView userName;
		public ImageView userImage;

	}

}
