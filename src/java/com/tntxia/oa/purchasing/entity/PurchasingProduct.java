package com.tntxia.oa.purchasing.entity;

import java.math.BigDecimal;

import com.tntxia.oa.sale.entity.SalePro;

public class PurchasingProduct {
	
	private int id;
	
	// 型号
	private String epro;
	
	// 年份
	private String cpro;
	
	// 封装
	private String pro_number;
	
	private int num;
	
	private int cgpro_num;
	
	private String money;
	
	// 货币类型
	private String hb;
	
	// 单位
	private String unit;
	
	// 采购价格
	private BigDecimal purchasingPrice;
	
	// 税率
	private Integer rate;
	
	// 品牌
	private String tradeMark;
	
	// 货期
	private  String deliverDate;
	
	private String supplier;
	
	private String wid;
	
	// 备注
	private String remark;
	
	private String cgpro_ydatetime;
	
	private String sale_rate;
	
	private BigDecimal selljg;
	
	// 仓库中剩下的数量
	private int warehouseNum;
	
	private String addr;
	
	private int sale_pro_id;
	
	private SalePro salePro;
	
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
	
	public int getCgpro_num() {
		return cgpro_num;
	}

	public void setCgpro_num(int cgpro_num) {
		this.cgpro_num = cgpro_num;
	}
	
	

	public String getHb() {
		return hb;
	}

	public void setHb(String hb) {
		this.hb = hb;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(BigDecimal purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
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
	
	

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getCgpro_ydatetime() {
		return cgpro_ydatetime;
	}

	public void setCgpro_ydatetime(String cgpro_ydatetime) {
		this.cgpro_ydatetime = cgpro_ydatetime;
	}

	public String getSale_rate() {
		return sale_rate;
	}

	public void setSale_rate(String sale_rate) {
		this.sale_rate = sale_rate;
	}

	public BigDecimal getSelljg() {
		return selljg;
	}

	public void setSelljg(BigDecimal selljg) {
		this.selljg = selljg;
	}

	public int getWarehouseNum() {
		return warehouseNum;
	}

	public void setWarehouseNum(int warehouseNum) {
		this.warehouseNum = warehouseNum;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public int getSale_pro_id() {
		return sale_pro_id;
	}

	public void setSale_pro_id(int sale_pro_id) {
		this.sale_pro_id = sale_pro_id;
	}

	public SalePro getSalePro() {
		return salePro;
	}

	public void setSalePro(SalePro salePro) {
		this.salePro = salePro;
	}
	
	public BigDecimal getSellTotal(){
		
		if(selljg==null){
			return BigDecimal.ZERO;
		}
		return selljg.multiply(BigDecimal.valueOf(num));
	}
	
}
