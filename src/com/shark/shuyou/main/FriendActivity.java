package com.shark.shuyou.main;

import java.util.ArrayList;

import com.shark.shuyou.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class FriendActivity extends Activity {
	private AppFoot foot = null;
	private ListView friendview = null;
	private FriendAdapter adapter = null;
	private ArrayList<FriendItem> friendlist = new ArrayList<FriendItem>(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_friend);
		
		foot = (AppFoot) this.findViewById(R.id.friend_foot);
		foot.setIndex(1);
		
		friendview = (ListView) this.findViewById(R.id.friend_list);
		FriendItem item = new FriendItem(0);
		item.setNick_name("书友官方");
		item.setUpdate_time(System.currentTimeMillis()/1000);
		this.friendlist.add(item);
		adapter = new FriendAdapter(this, R.layout.friend_item, this.friendlist);
		friendview.setAdapter(adapter);
	}

}
