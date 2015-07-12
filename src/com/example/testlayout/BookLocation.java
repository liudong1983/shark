package com.example.testlayout;

public class BookLocation {
	public String addr;
	public double lat;
	public double lont;
	public double radius;
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLont() {
		return lont;
	}
	public void setLont(double lont) {
		this.lont = lont;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public String getLoc() {
		StringBuffer sb = new StringBuffer(256);
		sb.append("地址:");
		sb.append(this.addr);
		sb.append(" 维度:");
		sb.append(this.lat);
		sb.append("经度:");
		sb.append(this.lont);
		return sb.toString();
	}

}
