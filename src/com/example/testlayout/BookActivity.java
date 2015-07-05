package com.example.testlayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.*;

public class BookActivity extends Activity {
	public DbHelper dbhelper = null;
	public BookItem item = null;
	public boolean flag = false;
	public boolean desc_flag = true;
	public TextView bookname_view = null;
	public TextView bookauthor_view = null;
	public TextView bookdesc_view = null;
	public ImageView image_view = null;
	public Button savebook_btn = null;
	public Spinner book_status_view = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_book);
		dbhelper = new DbHelper(this, "book_store.db", null, 1);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		this.item = (BookItem) bundle.getSerializable("book");
		image_view = (ImageView) this.findViewById(R.id.book_image);
		bookname_view = (TextView) this.findViewById(R.id.book_name);
		bookauthor_view = (TextView) this
				.findViewById(R.id.book_author);
		bookdesc_view = (TextView) this.findViewById(R.id.book_desc);
		bookdesc_view.setText(item.getBookDesc());
		savebook_btn = (Button) this.findViewById(R.id.save_book);
		this.book_status_view = (Spinner) this.findViewById(R.id.book_status_spin);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BookItem.stat_str);
		this.book_status_view.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.book_status_view.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				item.setBookStatus(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		flag = dbhelper.isexist(item);
		if (flag) {
			savebook_btn.setText("完成");
		}

		image_view.setImageBitmap(item.getBitmap());
		bookname_view.setText(item.getBookName());
		bookauthor_view.setText(item.getBookAuthor());

		savebook_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!flag) {
					if (dbhelper.insertBook(item)) {
						Toast.makeText(BookActivity.this, "插入成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(BookActivity.this, "插入成功",
								Toast.LENGTH_SHORT).show();
					}
					Intent intent = new Intent(BookActivity.this,
							MainActivity.class);
					startActivity(intent);
				} else {
					if (!dbhelper.saveBook(item)) {
						Toast.makeText(BookActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
					}
					finish();
				}
			}
		});

		bookdesc_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (desc_flag) {
					desc_flag = false;
					BookActivity.this.bookdesc_view.setEllipsize(null);
					BookActivity.this.bookdesc_view.setSingleLine(false);
				} else {
					desc_flag = true;
					BookActivity.this.bookdesc_view
							.setEllipsize(android.text.TextUtils.TruncateAt.END);
					BookActivity.this.bookdesc_view.setMaxLines(3);
				}
			}
		});
	}
}
