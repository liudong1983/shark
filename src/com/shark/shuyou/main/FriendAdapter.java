package com.shark.shuyou.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shark.shuyou.*;

public class FriendAdapter extends ArrayAdapter<FriendItem> {
	private int resid;
	public FriendAdapter(Context context, int resource, List<FriendItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resid = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FriendItem item = this.getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(this.resid, null);
		TextView friendName = (TextView) view.findViewById(R.id.friend_name);
		ImageView friendPrompt = (ImageView) view.findViewById(R.id.friend_prompt);
		friendName.setText(item.getNick_name());
		return view;
	}
	

}
