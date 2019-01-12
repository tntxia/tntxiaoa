package com.tntxia.oa.finance.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceIndexAction  extends HandlerWithHeaderAndLeftbar{

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("finance",-1,-1);
		
	}
}
