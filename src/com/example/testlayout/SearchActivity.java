package com.example.testlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.*;

public class SearchActivity extends Activity {
	public RelativeLayout layout1 = null;
	public TextView txt = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_serach);
		
		layout1 = (RelativeLayout) this.findViewById(R.id.search_layout);
		txt = (TextView) this.findViewById(R.id.search_txt);
		layout1.setClickable(true);
		layout1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchActivity.this.layout1.setAlpha(1f);
				Toast.makeText(SearchActivity.this, "search button click", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SearchActivity.this, SearchResult.class);
				startActivity(intent);
			}
		});
	}
}
