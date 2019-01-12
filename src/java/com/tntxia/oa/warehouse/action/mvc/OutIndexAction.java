package com.tntxia.oa.warehouse.action.mvc;

import java.util.Map;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class OutIndexAction  extends HandlerWithHeaderAndLeftbar{
	

	@Override
	public void init(WebRuntime runtime) throws Exception {

		Map<String,Object> root = this.getRoot();
		String ecompany = runtime.getSessionStr("ecompany");
		String username = runtime.getSessionStr("username");
		String dept = runtime.getSessionStr("dept");
		root.put("dept", dept);
		root.put("ecompany", ecompany);
		root.put("username", username);
		
		this.setLeftbar("warehouse",0,2);
		
		
		String role = runtime.getSessionStr("role");
		String loginTime = runtime.getSessionStr("currentDate");
		root.put("role", role);
		root.put("loginTime", loginTime);
		
		
		
	}
	
}
