package com.tntxia.oa.qc.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QcItem {
	
	private int id;
	
	private Date date;
	
	private String number;
	
	private String model;
	
	private String result;
	
	private String remark;
	
	private List<QcCheckItemResult> checkItemResultList = new ArrayList<QcCheckItemResult>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<QcCheckItemResult> getCheckItemResultList() {
		return checkItemResultList;
	}

	public void setCheckItemResultList(List<QcCheckItemResult> checkItemResultList) {
		this.checkItemResultList = checkItemResultList;
	}
	
	

}
