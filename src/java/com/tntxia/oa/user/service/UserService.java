package com.tntxia.oa.user.service;

import java.util.List;

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
	
	@SuppressWarnings("rawtypes")
	public List getSaleUserList() throws Exception {
		String sql = "select * from username where department_id in (select id from department where departname='销售部')";
		
		return dbManager.queryForList(sql, true);
	}

}
