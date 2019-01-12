package com.tntxia.oa.warehouse;

import java.util.Date;
import java.util.List;

public class InWarehouse {
	
	private int id;
	
	// 采购单的ID
	private int purchasingId;
	
	// 入库单号
	private String number;
	
	// 状态
	private String states;
	
	// 供应商
	private String supplier;
	
	private String int_types;
	
	private String man;
	
	private Date int_date;
	
	private String cg_number;
	
	private String sp_number;
	
	private String money;
	
	private String g_man;
	
	private String remark;
	
	
	
	public int getPurchasingId() {
		return purchasingId;
	}

	public void setPurchasingId(int purchasingId) {
		this.purchasingId = purchasingId;
	}

	public String getG_man() {
		return g_man;
	}

	public void setG_man(String g_man) {
		this.g_man = g_man;
	}

	@SuppressWarnings("rawtypes")
	private List rkhouseList;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getInt_types() {
		return int_types;
	}

	public void setInt_types(String int_types) {
		this.int_types = int_types;
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

	public String getCg_number() {
		return cg_number;
	}

	public void setCg_number(String cg_number) {
		this.cg_number = cg_number;
	}

	public String getSp_number() {
		return sp_number;
	}

	public void setSp_number(String sp_number) {
		this.sp_number = sp_number;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public List getRkhouseList() {
		return rkhouseList;
	}

	public void setRkhouseList(List rkhouseList) {
		this.rkhouseList = rkhouseList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
