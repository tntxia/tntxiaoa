package com.tntxia.oa.common;

public class CommonToolbarItem {
	
	private String text;
	
	private String action;
	
	public CommonToolbarItem(String text,String action) {
		this.text = text;
		this.action = action;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

}
