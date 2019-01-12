package com.tntxia.oa.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ≤÷ø‚»®œﬁ
 * @author tntxia
 *
 */
public class RestrainGP implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1052115355367957892L;

	private int id;
	
	private String name;
	
	private String warehouse;
	
	private int warehouseId;
	
	private boolean use;
	
	private boolean add;
	
	private boolean mod;
	
	private boolean del;
	
	private boolean price;
	
	private boolean view;
	
	private List<String> rightList = new ArrayList<String>();
	
	private int rightNum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrice() {
		return price;
	}

	public void setPrice(boolean price) {
		this.price = price;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public List<String> getRightList() {
		return rightList;
	}

	public void setRightList(List<String> rightList) {
		this.rightList = rightList;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isMod() {
		return mod;
	}

	public void setMod(boolean mod) {
		this.mod = mod;
	}

	public boolean isDel() {
		return del;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	

}
