package com.tntxia.oa.department.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class DepartmentAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		return dbManager.queryForList("select * from department", true);
	}

}
