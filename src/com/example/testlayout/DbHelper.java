package com.example.testlayout;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final String create_book_table = "create table if not exists book("
			+ "book_id integer primary key autoincrement,"
			+ "book_isbn varchar(50) not null unique,"
			+ "book_name varchar(50) not null,"
			+ "book_author1 varchar(50) default null,"
			+ "book_author2 varchar(50) default null,"
			+ "book_author3 varchar(50) default null,"
			+ "book_image blob,"
			+ "book_desc text,"
			+ "book_status integer,"
			+ "create_time integer,"
			+ "update_time timestamp not null default (datetime('now', 'localtime'))"
			+ ")";
	private Context _ctx = null;
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this._ctx = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(create_book_table);
		db.close();
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		db.execSQL(create_book_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public boolean insertBook(BookItem item) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("book_name", item.getBookName());
		values.put("book_author1", item.getBookAuthor1());
		values.put("book_author2", item.getBookAuthor2());
		values.put("book_author3", item.getBookAuthor3());
		values.put("book_desc", item.getBookDesc());
		values.put("book_status", item.getBookStatus());
		values.put("book_image", item.getbyteBitmap());
		values.put("create_time", new Date().getTime());
		values.put("book_isbn", item.getBookISBN());
		
		if (db.insert("book", null, values) == -1)
			return false;
		db.close();
		return true;
	}
	
	public ArrayList<BookItem> readALLBook() {
		ArrayList<BookItem> retlist = new ArrayList<BookItem>();
		BookItem item = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "select book_isbn, book_name, book_author1, book_author2, book_author3, book_image, book_desc, book_status from book order by update_time desc";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
			String book_isbn = cursor.getString(cursor.getColumnIndex("book_isbn"));
			String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
			String book_author1 = cursor.getString(cursor.getColumnIndex("book_author1"));
			String book_author2 = cursor.getString(cursor.getColumnIndex("book_author2"));
			String book_author3 = cursor.getString(cursor.getColumnIndex("book_author3"));
			byte[] book_image = cursor.getBlob(cursor.getColumnIndex("book_image"));
			String book_desc = cursor.getString(cursor.getColumnIndex("book_desc"));
			int book_status = cursor.getInt(cursor.getColumnIndex("book_status"));
			
			item = new BookItem(book_isbn);
			item.setbyteBitmap(book_image);
			item.setBookName(book_name);
			item.setBookAuthor(book_author1, book_author2, book_author3);
			item.setBookDesc(book_desc);
			item.setBookStatus(book_status);
			
			retlist.add(item);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return retlist;
	}
	
	public boolean isexist(BookItem item) {
		String query = "select count(*) from book where book_isbn =" + item.getBookISBN();
		int ret = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst())
		{
			ret = cursor.getInt(0);
		}
		cursor.close();
		db.close();
		if (ret >= 1)
			return true;
		return false;
	}
}
