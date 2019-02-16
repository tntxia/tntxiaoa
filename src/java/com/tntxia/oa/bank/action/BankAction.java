package com.tntxia.oa.bank.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class BankAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	public List listAll(WebRuntime runtime) throws Exception {
		String sql = "select * from sales_bank";
		return dbManager.queryForList(sql, true);
	}

}
