package com.tntxia.oa.print.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class PrintAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List getPrintHistory(WebRuntime runtime) throws Exception{
		
		String number = runtime.getParam("number");
		String sql = "select * from print_log where number = ?";
		return dbManager.queryForList(sql, new Object[]{number},true);
		
	}

}
