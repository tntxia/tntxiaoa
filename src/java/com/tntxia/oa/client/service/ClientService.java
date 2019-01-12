package com.tntxia.oa.client.service;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.service.CommonService;

public class ClientService extends CommonService{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String, Object> getClient(String id) throws Exception {

		String sql = "select * from client where clientid=?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

}
