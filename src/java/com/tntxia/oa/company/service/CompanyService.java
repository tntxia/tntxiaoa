package com.tntxia.oa.company.service;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class CompanyService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getDetail(Integer id) throws Exception{
		return dbManager.queryForMap("select * from company where id = ?",new Object[] {id},true);
	}
	
	public Map<String,Object> getDetail(String id) throws Exception{
		return this.getDetail(Integer.parseInt(id));
	}
	
	@SuppressWarnings("rawtypes")
	public List getList() throws Exception {
		return dbManager.queryForList("select * from company", true);
	}

}
