package com.shark.shuyou.main;

import com.shark.shuyou.*;
import com.shark.shuyou.zxing.activity.CaptureActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AppTitle extends RelativeLayout {
	private ImageButton add_book_btn = null;
	private ImageButton search_book_btn = null;
	private Context context = null;

	public AppTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.app_title, this);
		this.add_book_btn = (ImageButton) this.findViewById(R.id.add_book_item);
		this.search_book_btn = (ImageButton) this.findViewById(R.id.search_book_item);
		
		add_book_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AppTitle.this.context, CaptureActivity.class);
				AppTitle.this.context.startActivity(intent);
			}
		});
		
		search_book_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AppTitle.this.context, SearchActivity.class);
				AppTitle.this.context.startActivity(intent);
			}
		});
		
	}

}
