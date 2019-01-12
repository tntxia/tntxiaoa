package com.tntxia.oa.system.entity;

/**
 * 公告审批流程
 * @author tntxia
 *
 */
public class NoticeFlow {
	
	private int id;
	
	private Boolean needApproved;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getNeedApproved() {
		return needApproved;
	}

	public void setNeedApproved(Boolean needApproved) {
		this.needApproved = needApproved;
	}
	
	

}
