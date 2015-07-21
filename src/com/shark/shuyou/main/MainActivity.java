package com.shark.shuyou.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.shark.shuyou.*;
import com.shark.shuyou.zxing.activity.*;

public class MainActivity extends Activity {
	public final String TAG = MainActivity.class.getName();
	public static int select_flag = 0;
	private ArrayList<BookItem> _booklist = new ArrayList<BookItem>();
	private DbHelper dbhelper = null;
	private ListView listview = null;
	private AppFoot foot = null;
	private BookAdapter adapter = null;
	private BookItem intro_book = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		dbhelper = new DbHelper(this, "book_store.db", null, 1);
		listview = (ListView) this.findViewById(R.id.book_list);
		foot = (AppFoot) this.findViewById(R.id.shelf_foot);
		foot.setIndex(0);
		
		dbhelper.readALLBook(this._booklist);
		adapter = new BookAdapter(this, R.layout.book_item, this._booklist);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BookItem item = MainActivity.this._booklist.get(position);
				Intent intent = new Intent(MainActivity.this, BookActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("book", item);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		this.intro_book = new BookItem("001");
		Bitmap bmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.manual);
		intro_book.setBookAuthor("书友团队", null, null);
		intro_book.setBookName("app手册");
		intro_book.setBitmap(bmap);
		intro_book.setBookDesc("可以点击app菜单右上方 + 图标扫描图书，可以通过右上方的搜索图标，根据书名或者作者搜索图书, 接口使用豆瓣图书app");
		intro_book.setBookStatus(0);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dbhelper.readALLBook(this._booklist);
		if (this._booklist.size() == 0) {
			
			this._booklist.add(intro_book);
		} else
		this.adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
