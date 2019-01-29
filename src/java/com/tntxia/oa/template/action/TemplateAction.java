package com.tntxia.oa.template.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class TemplateAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 获取采购合同的列表
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception {
		String sql = "select * from ht_mb";
		return dbManager.queryForList(sql, true);
	}

}
