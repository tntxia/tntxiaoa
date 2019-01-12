package com.tntxia.oa.warehouse.entity;

public class InOutLog {
	
	private int id;
	
	private int wid;
	
	private int orginNum;
	
	private int changeNum;
	
	private int finalNum;
	
	private String number;
	
	private String username;
	
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public int getOrginNum() {
		return orginNum;
	}

	public void setOrginNum(int orginNum) {
		this.orginNum = orginNum;
	}

	public int getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(int changeNum) {
		this.changeNum = changeNum;
	}

	public int getFinalNum() {
		return finalNum;
	}

	public void setFinalNum(int finalNum) {
		this.finalNum = finalNum;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
