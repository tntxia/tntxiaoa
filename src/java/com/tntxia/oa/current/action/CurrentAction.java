package com.tntxia.oa.current.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class CurrentAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		String sql = "select * from hltable";
		return dbManager.queryForList(sql, true);
		
	}

}
