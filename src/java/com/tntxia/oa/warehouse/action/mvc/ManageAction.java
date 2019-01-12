package com.tntxia.oa.warehouse.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class ManageAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String role = this.getRole(runtime);
		
		if("总经理".equals(role) || "副总经理".equals(role)){
			this.getRoot().put("isManager", true);
		}
		
		
		this.setLeftbar("warehouse",0,0);
		
	}
	
}
