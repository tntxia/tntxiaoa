package com.tntxia.oa.finance.entity;

public class Gathering {
	
	private int id;
	
	private int saleId;
	
	private String coname;
	
	private String number;
	
	private double bank;
	
	/**
	 * ÒÑÊÕ¿î
	 */
	private double moneyGathered;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBank() {
		return bank;
	}

	public void setBank(double bank) {
		this.bank = bank;
	}

	public double getMoneyGathered() {
		return moneyGathered;
	}

	public void setMoneyGathered(double moneyGathered) {
		this.moneyGathered = moneyGathered;
	}
	
	

}
