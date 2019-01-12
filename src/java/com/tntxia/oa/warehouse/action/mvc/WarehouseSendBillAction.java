package com.tntxia.oa.warehouse.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseSendBillAction  extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("warehouse",1,0);
		
	}
	
}
