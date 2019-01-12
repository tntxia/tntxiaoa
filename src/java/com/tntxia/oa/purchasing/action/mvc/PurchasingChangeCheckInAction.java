package com.tntxia.oa.purchasing.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingChangeCheckInAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		return dbManager.queryForMap("select * from cgpro where id = ?", new Object[] {id},true);
	}
	

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");
		this.setRootValue("id", id);

		this.setRootValue("detail", this.getDetail(id));
		
	}

}
