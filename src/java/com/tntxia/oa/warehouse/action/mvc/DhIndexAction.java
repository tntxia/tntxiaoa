package com.tntxia.oa.warehouse.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class DhIndexAction  extends HandlerWithHeaderAndLeftbar{

	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		
		String ecompany = runtime.getSessionStr("ecompany");
		String username = runtime.getSessionStr("username");
		String dept = this.getDept(runtime);
		
		this.setRootValue("dept", dept);
		this.setRootValue("ecompany", ecompany);
		this.setRootValue("username", username);
		
		this.setLeftbar("warehouse",0,3);
		
		String role = this.getRole(runtime);
		String loginTime = runtime.getSessionStr("currentDate");
		this.setRootValue("role", role);
		this.setRootValue("loginTime", loginTime);
		
	}
	
}
