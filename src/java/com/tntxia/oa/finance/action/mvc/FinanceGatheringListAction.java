package com.tntxia.oa.finance.action.mvc;


import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceGatheringListAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		this.setLeftbar("finance",4,2);
		
		Map<String,Object> root = this.getRoot();
		
		root.put("depts", SystemCache.departmentList);
		root.put("users", SystemCache.saleUserList);
		
		String role = this.getRole(runtime);
		
		if("总经理".equals(role)){
			this.setRootValue("isManager", true);
		}
		
	}
}
