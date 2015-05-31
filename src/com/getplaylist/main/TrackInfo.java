package com.getplaylist.main;

public class TrackInfo {
	private String name;
	private String downloadUrl;
	public TrackInfo() {
		// TODO Auto-generated constructor stub
	}
	public TrackInfo(String name, String downloadUrl) {
		this.name =  name;
		this.downloadUrl = downloadUrl;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
}
