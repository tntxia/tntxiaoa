package com.tntxia.oa.warehouse.action.mvc;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FhIndexAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String ecompany = runtime.getSessionStr("ecompany");
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		setRootValue("dept", dept);
		setRootValue("ecompany", ecompany);
		setRootValue("username", username);
		
		this.setLeftbar("warehouse",1,1);
		
		
		String role = this.getRole(runtime);
		String loginTime = runtime.getSessionStr("currentDate");
		setRootValue("role", role);
		setRootValue("loginTime", loginTime);
		setRootValue("depts", SystemCache.departmentList);
		setRootValue("users", SystemCache.saleUserList);
		
		
	}
	
}
