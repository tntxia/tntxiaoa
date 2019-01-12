package com.tntxia.oa.checkwork.service;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.service.CommonService;

public class CheckworkService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List searchCheckworkRecord(String username,String startDate,String endDate) throws Exception {
		String sql = "select * from tntxiaoa_checkwork_record where user_name = ? and check_time>=? and check_time<?";
		return dbManager.queryForList(sql, new Object[]{username,startDate,endDate},true);
	}

}
