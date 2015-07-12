package com.example.testlayout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
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

import com.example.zxing.activity.*;
import com.example.*;

public class MainActivity extends Activity {
	public final String TAG = MainActivity.class.getName();
	public static int select_flag = 0;
	private List<BookItem> _booklist = null;
	private DbHelper dbhelper = null;
	private ListView listview = null;
	private AppFoot foot = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		dbhelper = new DbHelper(this, "book_store.db", null, 1);
		listview = (ListView) this.findViewById(R.id.book_list);
		foot = (AppFoot) this.findViewById(R.id.shelf_foot);
		foot.setIndex(0);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this._booklist = dbhelper.readALLBook();
		BookAdapter adapter = new BookAdapter(this, R.layout.book_item, this._booklist);
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
