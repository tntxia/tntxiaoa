package com.tntxia.oa.sale.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SaleNewAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime) throws Exception{
		String id1=runtime.getParam("id");
		String sql="select * from ht_mb where id='"+id1+"'";
		Map<String,Object> mb = dbManager.queryForMap(sql, true);
		this.setRootValue("tk", mb.get("q_tk"));
		this.setRootValue("remark", mb.get("remark"));
		
	}

}
