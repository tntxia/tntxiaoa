package com.tntxia.oa.unit.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class UnitAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from punit_type";
		
		return dbManager.queryForList(sql, true);
	}

}
