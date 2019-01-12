package com.tntxia.oa.sale.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author tntxia
 * 
 * 销售订单的产品
 *
 */
public class SalePro {
	
	private int id;
	
	private int ddid;
	
	private String coname;
	
	private String fypronum;

	private String custno;
	
	private String supplier;
	
	private String number;
	
	private String model;
	
	private BigDecimal salejg;
	
	private BigDecimal selljg;
	
	private String hb;
	
	private int num;
	
	private int numOut;
	
	private String mode;
	
	private double yj;
	
	private double yf;
	
	private String epro;
	
	private String cpro;
	
	private SaleFinanceInfo financeInfo;
	
	private String remark;
	
	private String man;
	
	private String state;
	
	private String money;
	
	private double profit;
	
	private String unit;
	
	private String deliveryDate;
	
	@SuppressWarnings("rawtypes")
	private List warehouseList;
	
	private String pro_tr;
	
	private int wid;
	
	private String mpn;

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
	
	public int getDdid() {
		return ddid;
	}

	public void setDdid(int ddid) {
		this.ddid = ddid;
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

	public BigDecimal getSalejg() {
		return salejg;
	}

	public void setSalejg(BigDecimal salejg) {
		this.salejg = salejg;
	}
	
	public BigDecimal getSelljg() {
		return selljg;
	}

	public void setSelljg(BigDecimal selljg) {
		this.selljg = selljg;
	}

	public String getHb() {
		return hb;
	}

	public void setHb(String hb) {
		this.hb = hb;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNumOut() {
		return numOut;
	}

	public void setNumOut(int numOut) {
		this.numOut = numOut;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public SaleFinanceInfo getFinanceInfo() {
		return financeInfo;
	}

	public void setFinanceInfo(SaleFinanceInfo financeInfo) {
		this.financeInfo = financeInfo;
	}

	public String getCpro() {
		return cpro;
	}

	public void setCpro(String cpro) {
		this.cpro = cpro;
	}

	public String getFypronum() {
		return fypronum;
	}

	public void setFypronum(String fypronum) {
		this.fypronum = fypronum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getTotalPrice() {
		
		BigDecimal numBigDecimal = BigDecimal.valueOf(num);
		
		return salejg.multiply(numBigDecimal);
	}

	public String getEpro() {
		return epro;
	}

	public void setEpro(String epro) {
		this.epro = epro;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public double getProfit() {
		return profit;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void calcProfit(double tax){
		
		// 如果采购或销售价为空，无法计算
		if(selljg==null || selljg.equals(BigDecimal.ZERO)){
			return;
	    }
		
		try{
			BigDecimal profitBig = salejg.subtract(selljg).subtract(salejg.multiply(BigDecimal.valueOf(tax))).divide(selljg,10,RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100));
			profit = profitBig.doubleValue();
		}catch(Exception e){
			
		}
		
	}

	@SuppressWarnings("rawtypes")
	public List getWarehouseList() {
		return warehouseList;
	}

	@SuppressWarnings("rawtypes")
	public void setWarehouseList(List warehouseList) {
		this.warehouseList = warehouseList;
	}

	public String getPro_tr() {
		return pro_tr;
	}

	public void setPro_tr(String pro_tr) {
		this.pro_tr = pro_tr;
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public String getMpn() {
		return mpn;
	}

	public void setMpn(String mpn) {
		this.mpn = mpn;
	}
	
	

}
