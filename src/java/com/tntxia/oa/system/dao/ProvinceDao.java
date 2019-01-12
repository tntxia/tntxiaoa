package com.tntxia.oa.system.dao;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.Province;

public class ProvinceDao {
	
	private DBManager dbManager;
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	@SuppressWarnings("rawtypes")
	public List list() throws Exception{
		String sql = "select id,province_name name from province order by id asc";
		return dbManager.queryForList(sql, Province.class);
	}
	

}
