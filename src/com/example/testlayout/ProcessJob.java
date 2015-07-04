package com.example.testlayout;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class ProcessJob implements Runnable {
	private static final String TAG = ProcessJob.class.getName();
	private String barcode = null;
	private final String douban_srv_url = "https://api.douban.com/v2/book/isbn/";
	private String bar_url = null;
	private Handler handler = null;
	private String json_str = null;
	private Context ctx = null;

	public ProcessJob(Context context, Handler handler, String code) {
		this.barcode = code;
		this.bar_url = this.douban_srv_url + this.barcode;
		this.handler = handler;
		this.ctx = context;
	}

	public boolean get_json_data() {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		StringBuilder rsp = new StringBuilder();
		String line;
		URL url = null;
		boolean flag = false;
		try {
			url = new URL(this.bar_url);
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
	
	public BookItem parse_json() {
		BookItem item = null;
		try {
			JSONObject jsonobj = new JSONObject(this.json_str);
			String isbn = jsonobj.getString("isbn13");
			String title = jsonobj.getString("title");
			String desc = jsonobj.getString("summary");
			String imageurl = jsonobj.getString("image");
			
			item = new BookItem(isbn);
			JSONArray author = jsonobj.getJSONArray("author");
			for (int idx = 0; idx < author.length() && idx < 3; ++idx) {
				String val = author.getString(idx);
				item.setBookAuthor(idx, val);
			}
			item.setBookDesc(desc);
			item.setBookName(title);
			item.setImageUrl(imageurl);
			item.setBookStatus(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "exception:" + e.toString());
		}
		return item;
	}
	
	public Bitmap down_image(BookItem book) {
		HttpURLConnection conn = null;
		URL url = null;
		Bitmap bitmap = null;
		InputStream is = null;
		
		try {
			url = new URL(book.getImageUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(1000);
			conn.setReadTimeout(1000);
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null)
			{
				conn.disconnect();
				conn = null;
			}
		}
		return bitmap;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = null;
		boolean flag = this.get_json_data();
		if (!flag) {
			msg = Message.obtain(this.handler, R.id.get_fail);
			msg.sendToTarget();
			return;
		}
		
		BookItem item = this.parse_json();
		if (item == null) {
			msg = Message.obtain(this.handler, R.id.parse_fail);
			msg.sendToTarget();
			return;
		}
		
		Bitmap bitmap = this.down_image(item);
		if (bitmap == null) {
			msg = Message.obtain(this.handler, R.id.get_fail);
			msg.sendToTarget();
			return;
		}
		
		item.setBitmap(bitmap);
		msg = Message.obtain(this.handler, R.id.parse_succ, item);
		msg.sendToTarget();
	}

}
