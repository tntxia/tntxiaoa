package com.tntxia.oa.sale.entity;

import java.util.ArrayList;
import java.util.List;

import com.tntxia.oa.common.NumberFactory;

public class Sale {
	
	private int id;
	
	private String coname;
	
	private String subck;
	
	private String sub;
	
	private String supplier;
	
	private String number;
	
	private String fy_number;
	
	private String co_number;
	
	private String model;
	
	private String state;
	
	private String custno;
	
	private String mode;
	
	private double yj;
	
	private double yf;
	
	// 合同总金额
	private double totalPrice;
	
	private SaleFinanceInfo financeInfo;
	
	// 采购订单号
	private String purchasingNumber;
	
	private String item;
	
	private String sendDate;
	
	private String man;
	
	private String spman;
	
	private String cwman;
	
	private String rate;
	
	private String currentType;
	
	private String trade;
	
	private String terms;
	
	private String remark;
	
	private String tr;
	
	private String address;
	
	private String deliveryLinkMan;
	
	private String deliveryTel;
	
	private String deliveryFeeType;
	
	private List<SalePro> proList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public double getYj() {
		return yj;
	}

	public void setYj(double yj) {
		this.yj = yj;
	}

	public double getYf() {
		return yf;
	}

	public void setYf(double yf) {
		this.yf = yf;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public SaleFinanceInfo getFinanceInfo() {
		return financeInfo;
	}

	public void setFinanceInfo(SaleFinanceInfo financeInfo) {
		this.financeInfo = financeInfo;
	}

	public String getPurchasingNumber() {
		return purchasingNumber;
	}

	public void setPurchasingNumber(String purchasingNumber) {
		this.purchasingNumber = purchasingNumber;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
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

	public String getFy_number() {
		return fy_number;
	}

	public void setFy_number(String fy_number) {
		this.fy_number = fy_number;
	}

	public String getCwman() {
		return cwman;
	}

	public void setCwman(String cwman) {
		this.cwman = cwman;
	}

	public String getSpman() {
		return spman;
	}

	public void setSpman(String spman) {
		this.spman = spman;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String generateNumber() throws Exception {
		this.number = NumberFactory.generateNumber("ONK-");
		return this.number;
	}

	public String getSubck() {
		return subck;
	}

	public void setSubck(String subck) {
		this.subck = subck;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDeliveryLinkMan() {
		return deliveryLinkMan;
	}

	public void setDeliveryLinkMan(String deliveryLinkMan) {
		this.deliveryLinkMan = deliveryLinkMan;
	}

	public String getDeliveryTel() {
		return deliveryTel;
	}

	public void setDeliveryTel(String deliveryTel) {
		this.deliveryTel = deliveryTel;
	}

	public String getDeliveryFeeType() {
		return deliveryFeeType;
	}

	public void setDeliveryFeeType(String deliveryFeeType) {
		this.deliveryFeeType = deliveryFeeType;
	}

	public List<SalePro> getProList() {
		return proList;
	}

	public void setProList(List<SalePro> proList) {
		this.proList = proList;
	}
	
	public void addPro(SalePro pro) {
		if(proList==null) {
			proList = new ArrayList<SalePro>();
		}
		proList.add(pro);
	}
	
	
}
