package com.tntxia.oa.payment.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class PaymentAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		String sql = "select * from payment_fs";
		return dbManager.queryForList(sql, true);
		
	}

}
