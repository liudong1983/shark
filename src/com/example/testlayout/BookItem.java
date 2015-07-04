package com.example.testlayout;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class BookItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String _name;
	private String _image_url;
	private byte[] _bitmap = null;
	private String _isbn = null;
	private String _author1 = null;
	private String _author2 = null;
	private String _author3 = null;
	private String _desc = null;
	private int _stat = 0;
	private static final String stat_str[] = {"收藏", "可借", "借出"};
	
	public BookItem(String isbn)
	{
		this._isbn = isbn;
	}
	
	public BookItem(String book_name, String book_author)
	{
		this._name = book_name;
		this._author1 = book_author;
	}
	
	public String getBookISBN()
	{
		return this._isbn;
	}
	
	public void setBookName(String name) {
		this._name = name;
	}
	public String getBookName() {
		return _name;
	}
	public void setBookAuthor(String author1, String author2, String author3) {
		this._author1 = author1;
		this._author2 = author2;
		this._author3 = author3;
	}
	public String getBookAuthor() {
		if (_author1 != null) {
			if (_author2 != null) {
				if (_author3 != null) {
					return _author1 + "," + _author2 + "," + _author3;
				}
				return _author1 + "," + _author2;
			}
			return _author1;
		}
		return null;
	}
	public void setImageUrl(String url) {
		this._image_url = url;
	}
	public String getImageUrl() {
		return this._image_url;
	}
	public void setBitmap(Bitmap bitmap) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 0, os);
		this._bitmap = os.toByteArray();
	}
	
	public byte[] getbyteBitmap() {
		return this._bitmap;
	}
	
	public void setbyteBitmap(byte[] bitmap) {
		this._bitmap = bitmap;
	}
	public Bitmap getBitmap() {
		return BitmapFactory.decodeByteArray(_bitmap, 0, _bitmap.length);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "name=" + this._name + " author:" + this.getBookAuthor();
	}
	
	public String getBookAuthor1() {
		return this._author1;
	}
	
	public String getBookAuthor2() {
		return this._author2;
	}
	
	public String getBookAuthor3() {
		return this._author3;
	}
	
	public String getBookDesc() {
		return _desc;
	}
	
	public void setBookDesc(String desc) {
		this._desc = desc;
	}
	
	public int getBookStatus() {
		return _stat;
	}
	
	public void setBookStatus(int stat) {
		this._stat = stat;
	}
	
	public String getBookStatusStr() {
		return stat_str[_stat];
	}
	
	public void setBookAuthor(int idx, String author) {
		if (idx == 0) {
			this._author1 = author;
		} else if(idx == 1) {
			this._author2 = author;
		} else if(idx == 2) {
			this._author3 = author;
		}
	}
}

