package com.example.testlayout;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ProcessSearch implements Runnable {
	private static final String TAG = ProcessSearch.class.getName();
	private final String douban_search_url = "https://api.douban.com/v2/book/search?count=50&q=";
	private String search_url = null;
	private Handler handler = null;
	private String json_str = null;
	private Context ctx = null;
	private String query = null;
	private String tag = null;
	private ArrayList<BookItem> booklist = null;
	
	public ProcessSearch(Context context, Handler handler, String query, String tag, ArrayList<BookItem> booklist) {
		this.query = query;
		this.tag = tag;
		this.search_url = this.douban_search_url + query;
		this.handler = handler;
		this.ctx = context;
		this.booklist = booklist;
	}
	
	public boolean get_search_data() {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		StringBuilder rsp = new StringBuilder();
		String line;
		URL url = null;
		boolean flag = false;
		try {
			url = new URL(this.search_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			if (conn.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					rsp.append(line);
				}
				this.json_str = rsp.toString();
				flag = true;
			}
			if (reader != null)
				reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return flag;
	}
	
	
	public boolean parse_json() {
		try {
			JSONObject search_json = new JSONObject(this.json_str);
			JSONArray books = search_json.getJSONArray("books");
			for (int book_idx = 0; book_idx < books.length(); ++book_idx) {
				JSONObject jsonobj = books.getJSONObject(book_idx);
				String isbn = jsonobj.getString("isbn13");
				String title = jsonobj.getString("title");
				String desc = jsonobj.getString("summary");
				String imageurl = jsonobj.getString("image");
				
				BookItem item = new BookItem(isbn);
				JSONArray author = jsonobj.getJSONArray("author");
				for (int idx = 0; idx < author.length() && idx < 3; ++idx) {
					String val = author.getString(idx);
					item.setBookAuthor(idx, val);
				}
				item.setBookDesc(desc);
				item.setBookName(title);
				item.setImageUrl(imageurl);
				item.setBookStatus(0);
				this.booklist.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "exception:" + e.toString());
			return false;
		}
		return true;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = null;
		boolean flag = this.get_search_data();
		if (!flag) {
			msg = Message.obtain(this.handler, R.id.get_fail);
			msg.sendToTarget();
			return;
		}
		
		if (!this.parse_json()) {
			msg = Message.obtain(this.handler, R.id.parse_fail);
			msg.sendToTarget();
			return;
		}
		
		msg = Message.obtain(this.handler, R.id.parse_succ);
		msg.sendToTarget();
	}

}
