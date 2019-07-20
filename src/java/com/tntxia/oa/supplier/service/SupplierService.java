package com.tntxia.oa.supplier.service;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class SupplierService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getSupplierByName(String name) throws Exception {
		String sql = "select cojsfs,coaddr from supplier where coname=?";
		return dbManager.queryForMap(sql, new Object[] {name}, true);
	}

}
