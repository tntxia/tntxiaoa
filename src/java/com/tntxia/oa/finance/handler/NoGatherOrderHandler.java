package com.tntxia.oa.finance.handler;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class NoGatherOrderHandler extends HandlerWithHeaderAndLeftbar {

	
	@Override
	public void init(WebRuntime runtime) {
		
		this.setLeftbar("finance", 4, 1);
		

	}

}
