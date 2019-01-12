package com.tntxia.oa.system.entity;

import java.util.Date;

/**
 * 用户职位
 * @author tntxia
 *
 */
public class Position {
	
	private int id;
	
	private String name;
	
	private String remark;
	
	private Date date;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
