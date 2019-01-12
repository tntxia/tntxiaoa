package com.tntxia.oa.client.handler;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ClientConversationHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	private List getConversationList(String id) throws Exception{
		String sql = "select * from customer_gj  where  coid='"+id+"'  order  by id  desc";
		return dbManager.queryForList(sql, true);
	}
	
	private int getConversationCount(String id) throws Exception{
		String sql = "select count(*) from customer_gj  where  coid='"+id+"'";
		return dbManager.queryForInt(sql);
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		root.put("id", id);
		root.put("isManager", "×Ü¾­Àí".equals(this.getRole(runtime)));
		root.put("conversationList", this.getConversationList(id));
		root.put("conversationCount", this.getConversationCount(id));
		
	}

}
