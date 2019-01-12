package com.tntxia.oa.warehouse;

public class Warehouse {
	
	private String id;
	
	/**
	 * 供应商
	 */
	private String co_name;
	
	private String pro_model;
	
	private String pro_gg;
	
	private String pro_name;
	
	private String pro_number;
	
	private String pro_type;
	
	private double saleprice;
	
	private String price_hb;
	
	private int pro_s_num;
	
	private int pro_num;
	
	private String pro_unit;
	
	private double pro_price;
	
	private String pro_addr;
	
	private String pro_supplier;
	
	private String pro_remark;
	
	private String sjnum;
	
	private String yqnum;
	
	private String yqdate;
	
	private String pro_secid;
	
	private String pro_sup_number;
	
	private int pro_min_num;
	
	private int pro_max_num;
	
	private String sale_states;
	
	private String sale_min_price;
	
	private String sale_max_price;
	
	private String pro_date;
	
	private String pro_man;
	
	private String pro_ms;
	
	private String pro_jstx;
	
	private String pro_yyfw;
	
	private int pin;
	
	private double js_price;
	
	private double zk_price;
	
	private int qnum;
	
	private String th_number;
	
	/**
	 * 总共的销售数量
	 */
	private int saleNumTotal;
	
	/**
	 * 总销售
	 */
	private double zs;
	
	/**
	 * 总采购
	 */
	private double zc;
	
	private String pro_weight_unit;	

	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getTh_number() {
		return th_number;
	}

	public void setTh_number(String th_number) {
		this.th_number = th_number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPro_model() {
		return pro_model;
	}

	public void setPro_model(String pro_model) {
		this.pro_model = pro_model;
	}

	public int getQnum() {
		return qnum;
	}

	public void setQnum(int qnum) {
		this.qnum = qnum;
	}

	public String getPro_gg() {
		return pro_gg;
	}

	public void setPro_gg(String pro_gg) {
		this.pro_gg = pro_gg;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getPro_number() {
		return pro_number;
	}

	public void setPro_number(String pro_number) {
		this.pro_number = pro_number;
	}

	public String getPro_type() {
		return pro_type;
	}

	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}

	public double getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}

	public String getPrice_hb() {
		return price_hb;
	}

	public void setPrice_hb(String price_hb) {
		this.price_hb = price_hb;
	}

	public int getPro_s_num() {
		return pro_s_num;
	}

	public void setPro_s_num(int pro_s_num) {
		this.pro_s_num = pro_s_num;
	}

	public int getPro_num() {
		return pro_num;
	}

	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}

	public String getPro_unit() {
		return pro_unit;
	}

	public void setPro_unit(String pro_unit) {
		this.pro_unit = pro_unit;
	}

	public double getPro_price() {
		return pro_price;
	}

	public void setPro_price(double pro_price) {
		this.pro_price = pro_price;
	}

	public String getPro_addr() {
		return pro_addr;
	}

	public void setPro_addr(String pro_addr) {
		this.pro_addr = pro_addr;
	}

	public String getPro_supplier() {
		return pro_supplier;
	}

	public void setPro_supplier(String pro_supplier) {
		this.pro_supplier = pro_supplier;
	}

	public String getPro_remark() {
		return pro_remark;
	}

	public void setPro_remark(String pro_remark) {
		this.pro_remark = pro_remark;
	}

	public String getSjnum() {
		return sjnum;
	}

	public void setSjnum(String sjnum) {
		this.sjnum = sjnum;
	}

	public String getYqnum() {
		return yqnum;
	}

	public void setYqnum(String yqnum) {
		this.yqnum = yqnum;
	}

	public String getYqdate() {
		return yqdate;
	}

	public void setYqdate(String yqdate) {
		this.yqdate = yqdate;
	}

	public String getPro_secid() {
		return pro_secid;
	}

	public void setPro_secid(String pro_secid) {
		this.pro_secid = pro_secid;
	}

	public String getPro_sup_number() {
		return pro_sup_number;
	}

	public void setPro_sup_number(String pro_sup_number) {
		this.pro_sup_number = pro_sup_number;
	}

	public int getPro_min_num() {
		return pro_min_num;
	}

	public void setPro_min_num(int pro_min_num) {
		this.pro_min_num = pro_min_num;
	}

	public int getPro_max_num() {
		return pro_max_num;
	}

	public void setPro_max_num(int pro_max_num) {
		this.pro_max_num = pro_max_num;
	}

	public String getSale_states() {
		return sale_states;
	}

	public void setSale_states(String sale_states) {
		this.sale_states = sale_states;
	}

	public String getSale_min_price() {
		return sale_min_price;
	}

	public void setSale_min_price(String sale_min_price) {
		this.sale_min_price = sale_min_price;
	}

	public String getSale_max_price() {
		return sale_max_price;
	}

	public void setSale_max_price(String sale_max_price) {
		this.sale_max_price = sale_max_price;
	}

	public String getPro_date() {
		return pro_date;
	}

	public void setPro_date(String pro_date) {
		this.pro_date = pro_date;
	}

	public String getPro_man() {
		return pro_man;
	}

	public void setPro_man(String pro_man) {
		this.pro_man = pro_man;
	}

	public String getPro_ms() {
		return pro_ms;
	}

	public void setPro_ms(String pro_ms) {
		this.pro_ms = pro_ms;
	}

	public String getPro_jstx() {
		return pro_jstx;
	}

	public void setPro_jstx(String pro_jstx) {
		this.pro_jstx = pro_jstx;
	}

	public String getPro_yyfw() {
		return pro_yyfw;
	}

	public void setPro_yyfw(String pro_yyfw) {
		this.pro_yyfw = pro_yyfw;
	}

	public double getJs_price() {
		return js_price;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public void setJs_price(double js_price) {
		this.js_price = js_price;
	}

	public double getZk_price() {
		return zk_price;
	}

	public void setZk_price(double zk_price) {
		this.zk_price = zk_price;
	}

	public int getSaleNumTotal() {
		return saleNumTotal;
	}

	public void setSaleNumTotal(int saleNumTotal) {
		this.saleNumTotal = saleNumTotal;
	}

	public double getZs() {
		return zs;
	}

	public void setZs(double zs) {
		this.zs = zs;
	}

	public double getZc() {
		return zc;
	}

	public void setZc(double zc) {
		this.zc = zc;
	}

	/**
	 * 获取利润的值
	 * 利润 = 销售值-采购值
	 * @return
	 */
	public double getLr() {
		return zs-zc;
	}

	/**
	 * 获取利润率的值
	 * 利润率 = （销售值-采购值）/销售值
	 * @return
	 */
	public double getLrv() {
		return (zs-zc)/zs;
	}

	public String getPro_weight_unit() {
		return pro_weight_unit;
	}

	public void setPro_weight_unit(String pro_weight_unit) {
		this.pro_weight_unit = pro_weight_unit;
	}


}
