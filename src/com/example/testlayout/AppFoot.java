package com.example.testlayout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.*;

public class AppFoot extends LinearLayout {
	public Context ctx = null;
	private ImageButton book_shelf_button = null;
	private ImageButton book_friend_button = null;
	private ImageButton book_person_button = null;
	public int select_idx = 0;

	public void setIndex(int idx) {
		this.select_idx = idx;
		if (this.select_idx == 0) {
			this.book_shelf_button.setImageResource(R.drawable.book_selected);
		} else if (this.select_idx == 1) {
			this.book_friend_button.setImageResource(R.drawable.book_selected);
		} else {
			this.book_person_button.setImageResource(R.drawable.book_selected);
		}
	}

	public AppFoot(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		LayoutInflater.from(context).inflate(R.layout.app_foot, this);
		this.book_shelf_button = (ImageButton) this
				.findViewById(R.id.button_book_shelf);
		this.book_friend_button = (ImageButton) this
				.findViewById(R.id.button_book_friend);
		this.book_person_button = (ImageButton) this
				.findViewById(R.id.button_book_person);

		this.book_shelf_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (select_idx != 0) {
					Intent intent = new Intent(ctx, MainActivity.class);
					ctx.startActivity(intent);
				}
			}
		});

		this.book_friend_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (select_idx != 1) {
					Intent intent = new Intent(ctx, SearchActivity.class);
					ctx.startActivity(intent);
				}
			}
		});

		this.book_person_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (select_idx != 2) {
					Intent intent = new Intent(ctx, PersonActivity.class);
					ctx.startActivity(intent);
				}
			}
		});
	}
}
