package com.example.testlayout;

import com.example.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class PersonActivity extends Activity {
	public DbHelper helper = null;
	public TextView loc_view = null;
	private AppFoot foot = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.person_info);
		
		helper = new DbHelper(this, "book_store", null, 1);
		loc_view = (TextView) this.findViewById(R.id.locview);
		foot = (AppFoot) this.findViewById(R.id.person_foot);
		foot.setIndex(2);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BookApplication app = (BookApplication) this.getApplication();
		app.mLocationClient.start();
		BookLocation loc = helper.getLoc();
		if (loc == null) {
			loc_view.setText("未知，定位中");
		} else {
			loc_view.setText(loc.getAddr());
		}
	}

}
