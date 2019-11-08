package com.tntxia.oa.knowledge.handler;

import com.tntxia.common.date.DateUtil;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.oa.knowledge.service.KnowledgeService;
import com.tntxia.web.mvc.WebRuntime;

public class KnowledgeNewAction extends HandlerWithHeader  {
	
	private KnowledgeService service = new KnowledgeService();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		this.setRootValue("typeList", service.getTypeList());
		this.setRootValue("username", this.getUsername(runtime));
		this.setRootValue("currentDate", DateUtil.getCurrentDateSimpleStr());
	}
}
