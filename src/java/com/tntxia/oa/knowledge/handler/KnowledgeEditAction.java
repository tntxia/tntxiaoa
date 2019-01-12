package com.tntxia.oa.knowledge.handler;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.oa.knowledge.service.KnowledgeService;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeEditAction extends HandlerWithHeader  {
	
	private DBManager dbManager = this.getDBManager();
	
	private KnowledgeService service = new KnowledgeService();
	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		this.setRootValue("typeList", service.getTypeList());
		this.setRootValue("zskmod", this.existRight(runtime, "zskmod"));
		this.setRootValue("zskdel", this.existRight(runtime, "zskdel"));
		
		String sql = "select * from lawtable where id=?";
		
		this.getRoot().putAll(dbManager.queryForMap(sql,new Object[]{id}, true));
		
	}
}
