package com.tntxia.oa.warehouse.entity;

import java.util.Date;

/**
 * 入库单产品
 * 
 * @author tntxia
 *
 */
public class WarehouseInProduct {
	
	private int id;
	
	private int inId;
	
	private String inNumber;
	
	// 采购订单号
	private String purchaseNumber;
	
	private int warehouseInId;
	
	// 型号
	private String model;
	
	// 年份
	private String productYear;
	
	private int num;
	
	private String remark;
	
	// 入库单状态
	private String states;
	
	private String supplier;
	
	private String hb;
	
	private double price;
	
	private String addr;
	
	private String man;
	
	private Date int_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public int getInId() {
		return inId;
	}

	public void setInId(int inId) {
		this.inId = inId;
	}

	public String getInNumber() {
		return inNumber;
	}

	public void setInNumber(String inNumber) {
		this.inNumber = inNumber;
	}

	public int getWarehouseInId() {
		return warehouseInId;
	}

	public void setWarehouseInId(int warehouseInId) {
		this.warehouseInId = warehouseInId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getProductYear() {
		return productYear;
	}

	public void setProductYear(String productYear) {
		this.productYear = productYear;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getHb() {
		return hb;
	}

	public void setHb(String hb) {
		this.hb = hb;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public Date getInt_date() {
		return int_date;
	}

	public void setInt_date(Date int_date) {
		this.int_date = int_date;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}
	
	

}
