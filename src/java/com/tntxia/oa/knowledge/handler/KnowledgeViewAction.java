package com.tntxia.oa.knowledge.handler;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeViewAction extends HandlerWithHeader  {
	
	private DBManager dbManager = this.getDBManager();
	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		this.setRootValue("zskmod", this.existRight(runtime, "zskmod"));
		this.setRootValue("zskdel", this.existRight(runtime, "zskdel"));
		
		String sql = "select l.id,l.titel,l.km_fa,l.username,l.fvdate,l.content,k.km_name,l.fvdate from lawtable l left outer join km_ty k on l.km_types = k.id   where   l.id=?";
		
		this.getRoot().putAll(dbManager.queryForMap(sql,new Object[]{id}, true));
		
	}
}
