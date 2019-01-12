package com.tntxia.oa.sale.entity;

import java.math.BigDecimal;

/**
 * ÍË»õ²úÆ·
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class RefundPro {
	
	private Integer id;
	
	private String model;
	
	private String productYear;
	
	private int num;
	
	private String seal;
	
	private String supplier;
	
	private String unit;
	
	private BigDecimal salejg;
	
	private String salehb;
	
	private BigDecimal salehj;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductYear() {
		return productYear;
	}

	public void setProductYear(String productYear) {
		this.productYear = productYear;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSeal() {
		return seal;
	}

	public void setSeal(String seal) {
		this.seal = seal;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getSalejg() {
		return salejg;
	}

	public void setSalejg(BigDecimal salejg) {
		this.salejg = salejg;
	}

	public String getSalehb() {
		return salehb;
	}

	public void setSalehb(String salehb) {
		this.salehb = salehb;
	}

	public BigDecimal getSalehj() {
		return salejg.multiply(BigDecimal.valueOf(num));
	}

	public void setSalehj(BigDecimal salehj) {
		this.salehj = salehj;
	}
	
	

}
