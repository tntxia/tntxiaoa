package com.tntxia.oa.system.dao;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.Country;

public class CountryDao {
	
	private DBManager dbManager;
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public List list() throws Exception{
		String sql = "select id,coun_name name from country order by id asc";
		return dbManager.queryForList(sql, Country.class);
	}
	

}
