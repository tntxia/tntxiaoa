package com.tntxia.oa.knowledge.service;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.service.CommonService;

public class KnowledgeService extends CommonService{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List getTypeList() throws Exception{
		String sql = "select * from km_ty";
		return dbManager.queryForList(sql, true);
	}

}
