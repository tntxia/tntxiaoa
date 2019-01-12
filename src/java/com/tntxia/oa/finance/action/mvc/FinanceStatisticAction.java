package com.tntxia.oa.finance.action.mvc;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceStatisticAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("finance", 4, 6);
		setRootValue("depts", SystemCache.departmentList);
		setRootValue("users", SystemCache.saleUserList);
		setRootValue("skhzadd", super.existRight(runtime, "skhzadd"));
		
	}
}
