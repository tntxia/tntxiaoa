package com.tntxia.oa.qc.entity;

public class QcCheckItemResult {
	
	private int id;
	
	private int qcId;
	
	private int checkItemId;
	
	private String checkItemName;
	
	private String result;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQcId() {
		return qcId;
	}

	public void setQcId(int qcId) {
		this.qcId = qcId;
	}

	public int getCheckItemId() {
		return checkItemId;
	}

	public void setCheckItemId(int checkItemId) {
		this.checkItemId = checkItemId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCheckItemName() {
		return checkItemName;
	}

	public void setCheckItemName(String checkItemName) {
		this.checkItemName = checkItemName;
	}
	
	
	
}
