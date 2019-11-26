package com.tntxia.oa.purchasing.entity;

import java.util.Date;

import com.tntxia.web.util.UUIDUtils;

public class PurchasingAuditLog {
	
	private String id = UUIDUtils.getUUID();
	
	private String orderId;
	
	private String operator;
	
	private String statusFrom;
	
	private String statusTo;
	
	private Date operateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatusFrom() {
		return statusFrom;
	}

	public void setStatusFrom(String statusFrom) {
		this.statusFrom = statusFrom;
	}

	public String getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(String statusTo) {
		this.statusTo = statusTo;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	

}
