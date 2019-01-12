package com.tntxia.oa.system.entity;

import java.util.ArrayList;
import java.util.List;

public class Restrain {
	
	private int id;
	
	private String name;
	
	private String intadd;
	
	private String intview;
	
	private String cgmod;
	
	private String cgdel;
	
	private String subadd;
	
	private String subview;
	
	private List<String> rightList = new ArrayList<String>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntadd() {
		return intadd;
	}

	public void setIntadd(String intadd) {
		this.intadd = intadd;
	}

	public String getIntview() {
		return intview;
	}

	public void setIntview(String intview) {
		this.intview = intview;
	}

	public String getCgmod() {
		return cgmod;
	}

	public void setCgmod(String cgmod) {
		this.cgmod = cgmod;
	}

	public String getCgdel() {
		return cgdel;
	}

	public void setCgdel(String cgdel) {
		this.cgdel = cgdel;
	}

	public String getSubadd() {
		return subadd;
	}

	public void setSubadd(String subadd) {
		this.subadd = subadd;
	}

	public String getSubview() {
		return subview;
	}

	public void setSubview(String subview) {
		this.subview = subview;
	}

	public List<String> getRightList() {
		return rightList;
	}

	public void setRightList(List<String> rightList) {
		this.rightList = rightList;
	}
	
}
