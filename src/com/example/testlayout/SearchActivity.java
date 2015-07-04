package com.example.testlayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.*;
import com.example.zxing.activity.CaptureActivity;

public class SearchActivity extends Activity {
	public RelativeLayout layout1 = null;
	public TextView txt = null;
	public ArrayList<BookItem> booklist = new ArrayList<BookItem>();
	private DbHelper dbhelper = null;
	private SearchHintAdapter adapter = null;
	private Handler search_handler = null;
	private Handler post_handler = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_serach);
		dbhelper = new DbHelper(this, "book_store.db", null, 1);
		
		this.adapter = new SearchHintAdapter(SearchActivity.this, R.layout.search_hint, booklist);
		ListView search_hint_list = (ListView) this.findViewById(R.id.search_hint_list);
		search_hint_list.setAdapter(adapter);
		
		this.post_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what) {
				case R.id.get_fail:
					Toast.makeText(SearchActivity.this, "get fail", Toast.LENGTH_SHORT).show();
					break;
				case R.id.parse_succ:
					BookItem item = (BookItem) msg.obj;
					Intent intent = new Intent(SearchActivity.this, BookActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("book", item);
					intent.putExtras(bundle);
					startActivity(intent);
					break;
				case R.id.parse_fail:
					Toast.makeText(SearchActivity.this, "parse fail", Toast.LENGTH_SHORT).show();
					break;
				case R.id.save_fail:
					Toast.makeText(SearchActivity.this, "save fail", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};
		search_hint_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BookItem cur_item = booklist.get(position);
				BookItem item = dbhelper.readBook(cur_item.getBookISBN());
				if (item != null) {
					Intent intent = new Intent(SearchActivity.this, BookActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("book", item);
					intent.putExtras(bundle);
					startActivity(intent);
					return;
				}
				ProcessJob job = new ProcessJob(SearchActivity.this, post_handler, cur_item.getBookISBN());
				Thread t = new Thread(job);
				t.start();
			}
		});
		
		this.search_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what) {
				case R.id.get_fail:
					Toast.makeText(SearchActivity.this, "get fail", Toast.LENGTH_SHORT).show();
					break;
				case R.id.parse_succ:
					SearchActivity.this.adapter.notifyDataSetChanged();
					break;
				case R.id.parse_fail:
					Toast.makeText(SearchActivity.this, "parse fail", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};
		
		SearchView search_view = (SearchView) this.findViewById(R.id.search_view);
		search_view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				SearchActivity.this.booklist.clear();
				SearchActivity.this.dbhelper.readSearchBook(query, SearchActivity.this.booklist);
				String url_query = null;
				try {
					url_query = URLEncoder.encode(query, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ProcessSearch searchjob = new ProcessSearch(SearchActivity.this, SearchActivity.this.search_handler, 
						url_query, null, SearchActivity.this.booklist);
				Thread t = new Thread(searchjob);
				t.start();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				SearchActivity.this.booklist.clear();
				SearchActivity.this.dbhelper.readSearchBook(newText, SearchActivity.this.booklist);
				SearchActivity.this.adapter.notifyDataSetChanged();
				return true;
			}
		});
		
		
		
	}
}
