package com.tntxia.oa.main.action.mvc;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeNewAction extends HandlerWithHeader  {
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		String strSQL = "select * from km_ty";
		this.setRootValue("typeList", dbManager.queryForList(strSQL, true));
		this.setRootValue("username", this.getUsername(runtime));
		this.setRootValue("currentDate", DateUtil.getCurrentDateSimpleStr());
	}
}
