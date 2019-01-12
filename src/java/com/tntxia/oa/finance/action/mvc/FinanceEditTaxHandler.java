package com.tntxia.oa.finance.action.mvc;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceEditTaxHandler  extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		String sql="select  * from gathering where id='"+id+"'";
		
		
		this.putAllRootValue(dbManager.queryForMap(sql, true));
		
		
	}
}
