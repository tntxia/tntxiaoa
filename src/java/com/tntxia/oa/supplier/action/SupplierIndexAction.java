package com.tntxia.oa.supplier.action;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class SupplierIndexAction extends HandlerWithHeaderAndLeftbar{

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("purchasing", 0, 0);
		
		
	}

}
