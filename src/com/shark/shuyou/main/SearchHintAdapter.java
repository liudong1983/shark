package com.shark.shuyou.main;

import java.util.List;

import com.shark.shuyou.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchHintAdapter extends ArrayAdapter<BookItem> {
	private int resourceId;

	public SearchHintAdapter(Context context, int resource, List<BookItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		BookItem book = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(R.layout.search_hint, null);
		TextView book_name = (TextView) view.findViewById(R.id.book_name_hint);
		TextView book_author = (TextView) view.findViewById(R.id.book_author_hint);
		
		book_name.setText(book.getBookName());
		book_author.setText(book.getBookAuthor());
		return view;
	}

}
