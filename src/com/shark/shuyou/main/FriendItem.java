package com.shark.shuyou.main;

public class FriendItem {
	private int id;
	private String nick_name;
	private long update_time;
	private long read_time;
	private byte[] _bitmap = null;
	
	public FriendItem(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}


	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public long getRead_time() {
		return read_time;
	}

	public void setRead_time(long read_time) {
		this.read_time = read_time;
	}

	public byte[] get_bitmap() {
		return _bitmap;
	}

	public void set_bitmap(byte[] _bitmap) {
		this._bitmap = _bitmap;
	}
	
	
}
