package com.tntxia.oa.user.service;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class UserService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getUserDept(String username) throws Exception {
		String sql = "select department_id from username where name=?";
		Integer deptId = dbManager.queryForInt(sql,new Object[] {username});
		sql = "select * from department where id = ?";
		return dbManager.queryForMap(sql,new Object[] {deptId});
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getSaleUserList() throws Exception {
		String sql = "select * from username where department_id in (select id from department where departname='销售部')";
		return dbManager.queryForList(sql, true);
	}
	
	public boolean existUser(String name) throws Exception {
		String sql = "select count(*) from username where name = ?";
		return dbManager.exist(sql, new Object[] {name});
	}

}
