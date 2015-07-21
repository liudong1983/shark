package com.shark.shuyou.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shark.shuyou.*;

public class BookAdapter extends ArrayAdapter<BookItem> {
	
	private int _resid;

	public BookAdapter(Context context, int resource, List<BookItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this._resid = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		BookItem item = this.getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(this._resid, null);
		TextView bookname = (TextView)view.findViewById(R.id.book_name);
		TextView bookauthor = (TextView)view.findViewById(R.id.book_author);
		TextView bookstat = (TextView) view.findViewById(R.id.book_stat);
		bookstat.setText(item.getBookStatusStr());
		bookname.setText(item.getBookName());
		bookauthor.setText(item.getBookAuthor());
		return view;
	}
	
	
}
