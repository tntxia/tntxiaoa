package com.tntxia.oa.back;

import infocrmdb.infocrmdb;

import java.sql.Date;
import java.sql.ResultSet;

public class ThTable {
	
	private int id;
	
	private String number;
	
	private String coname;
	
	private String co_number;
	
	private String man;
	
	private String money;
	
	private Date datetime;
	
	private String dept;
	
	private String deptjb;
	
	private String sub;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptjb() {
		return deptjb;
	}

	public void setDeptjb(String deptjb) {
		this.deptjb = deptjb;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getCo_number() {
		return co_number;
	}

	public void setCo_number(String co_number) {
		this.co_number = co_number;
	}

	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	
}
