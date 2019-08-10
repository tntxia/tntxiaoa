package com.tntxia.oa.user.entity;

public class UserToDoItem {
	private String label;
	
	private String url;
	
	private int count;
	
	public UserToDoItem(String label,String url,int count) {
		this.label = label;
		this.count = count;
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
