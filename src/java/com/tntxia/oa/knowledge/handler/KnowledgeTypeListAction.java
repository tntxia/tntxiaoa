package com.tntxia.oa.knowledge.handler;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeTypeListAction extends HandlerWithHeader  {
	
	private DBManager dbManager = this.getDBManager();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		String strSQL = "select  id,km_name from km_ty  order by  km_name asc";
		List list = dbManager.queryForList(strSQL, true);
		this.setRootValue("list", list);
		
	}
}
