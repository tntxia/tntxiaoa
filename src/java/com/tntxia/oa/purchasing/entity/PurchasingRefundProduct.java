package com.tntxia.oa.purchasing.entity;

public class PurchasingRefundProduct {
	
	private int id;
	
	// 型号
	private String epro;
	
	// 年份
	private String cpro;
	
	// 封装
	private String pro_number;
	
	private int num;
	
	// 货币类型
	private String unit;
	
	// 采购价格
	private double purchasingPrice;
	
	// 税率
	private String rate;
	
	// 品牌
	private String tradeMark;
	
	// 货期
	private  String deliverDate;
	
	// 备注
	private String remark;
	
	// 采购单号
	private String purchasingNumber;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEpro() {
		return epro;
	}

	public void setEpro(String epro) {
		this.epro = epro;
	}

	public String getCpro() {
		return cpro;
	}

	public void setCpro(String cpro) {
		this.cpro = cpro;
	}

	public String getPro_number() {
		return pro_number;
	}

	public void setPro_number(String pro_number) {
		this.pro_number = pro_number;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(double purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPurchasingNumber() {
		return purchasingNumber;
	}

	public void setPurchasingNumber(String purchasingNumber) {
		this.purchasingNumber = purchasingNumber;
	}

	
	
}
