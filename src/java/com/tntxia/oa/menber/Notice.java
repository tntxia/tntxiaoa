package com.tntxia.oa.menber;

import java.util.Date;

public class Notice {
	
	private int id;
	
	private String pro_man;
	
	private Date pro_time;
	
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPro_man() {
		return pro_man;
	}

	public void setPro_man(String pro_man) {
		this.pro_man = pro_man;
	}

	public Date getPro_time() {
		return pro_time;
	}

	public void setPro_time(Date pro_time) {
		this.pro_time = pro_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
