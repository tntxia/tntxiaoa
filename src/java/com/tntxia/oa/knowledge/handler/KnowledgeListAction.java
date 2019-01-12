package com.tntxia.oa.knowledge.handler;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeListAction extends HandlerWithHeader  {
	
	private DBManager dbManager = this.getDBManager();

	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String typeId = runtime.getParam("typeId");
		String strSQL = "select * from km_ty where id = ?  order by  km_name asc";
		Map<String,Object> type = dbManager.queryForMap(strSQL,new Object[]{typeId}, true);
		this.getRoot().putAll(type);
		
		this.setRootValue("zskadd", this.existRight(runtime, "zskadd"));
		
		String sql = "select l.id,l.titel,k.km_name,l.fvdate from lawtable l left outer join km_ty k on l.km_types = k.id   where   km_types=?   order by id desc";
		this.setRootValue("list", dbManager.queryForList(sql,new Object[]{typeId}, true));
		
		
	}
}
