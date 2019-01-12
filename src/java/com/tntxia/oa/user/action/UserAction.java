package com.tntxia.oa.user.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class UserAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception {
		return dbManager.queryForList("select * from username", true);
	}
	
	public Map<String,Object> changePass(WebRuntime runtime) throws Exception{
		
		String username = this.getUsername(runtime);
		String password = runtime.getParam("password");
		
		String sql = "update username set password = ? where name = ?";
		
		dbManager.update(sql, new Object[]{password,username});
		
		return this.success();
	}

}
