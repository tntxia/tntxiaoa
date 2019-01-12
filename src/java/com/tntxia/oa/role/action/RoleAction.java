package com.tntxia.oa.role.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class RoleAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		return dbManager.queryForList("select role_name from role", true);
	}

}
