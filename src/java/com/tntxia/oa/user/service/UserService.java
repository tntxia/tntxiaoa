package com.tntxia.oa.user.service;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class UserService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	public String getUserDept(String username) throws Exception {
		String sql = "select department_id from username where name=?";
		Integer deptId = dbManager.queryForInt(sql,new Object[] {username});
		sql = "select departname from department where id = ?";
		return dbManager.getString(sql,new Object[] {deptId});
		
	}

}
