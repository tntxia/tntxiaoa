package com.tntxia.oa.finance.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceGatheredListAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("finance",4,3);
		
		this.setRootValue("depts", SystemCache.departmentList);
		this.setRootValue("users", SystemCache.saleUserList);
		
	}
}
