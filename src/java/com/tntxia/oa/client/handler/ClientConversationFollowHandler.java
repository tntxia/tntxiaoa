package com.tntxia.oa.client.handler;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ClientConversationFollowHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private String getConame(String id) throws Exception{
		String sql = "select coname from client where clientId='"+id+"'";
		return dbManager.getString(sql);
	}
	
	@SuppressWarnings("rawtypes")
	private List getActivityTypes() throws Exception{
		String sql = "select * from activity_type";
		return dbManager.queryForList(sql, true);
	}
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql = "select * from customer_gj where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
		
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		Map<String,Object> detail = this.getDetail(id);
		
		root.put("id", id);
		root.put("username", this.getUsername(runtime));
		root.put("dept", this.getDept(runtime));
		root.put("deptjb", this.getDeptjb(runtime));
		root.put("coname", this.getConame(id));
		root.put("activityTypes",this.getActivityTypes());
		root.putAll(detail);

	}

}
