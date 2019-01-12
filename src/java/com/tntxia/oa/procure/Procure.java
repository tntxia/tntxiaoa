package com.tntxia.oa.procure;

import java.util.List;

public class Procure {
	
	private int id;                // 采购订单id
	
	private String number;			// 采购订单编号
	
	private String man;             // 负责人
	
	private String l_spyj;          // 审批意见
	
	private String sub;             // 销售订单号
	
	private String money;           // 采购货币
	
	private String co_name;         // 供应商名称
	
	private List<CgPro> pros;       // 订单产品
	
	private String jydd;            // 交易地点
	
	
	

	public String getJydd() {
		return jydd;
	}

	public void setJydd(String jydd) {
		this.jydd = jydd;
	}

	public List<CgPro> getPros() {
		return pros;
	}

	public void setPros(List<CgPro> pros) {
		this.pros = pros;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getL_spyj() {
		return l_spyj;
	}

	public void setL_spyj(String l_spyj) {
		this.l_spyj = l_spyj;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}
	
	

}
