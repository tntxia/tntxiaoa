package com.tntxia.oa.finance.action.mvc;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceExchangeAction  extends HandlerWithHeaderAndLeftbar{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List getAccountTypeList() throws Exception{
		String sql = "select * from km";
		return dbManager.queryForList(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getAccountDetailTypeList() throws Exception{
		String sql = "select * from km_mx";
		return dbManager.queryForList(sql, true);
	}

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("finance",2,0);
		this.setRootValue("accountTypes", this.getAccountTypeList());
		this.setRootValue("accountDetailTypes", this.getAccountDetailTypeList());
		this.setRootValue("r_wlz_add", this.existRight(runtime, "r_wlz_add"));
	}
}
