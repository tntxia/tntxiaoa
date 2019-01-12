package com.tntxia.oa.sale.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class SaleDraftListAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception{
		
		this.setRootValue("hasAddRight", this.existRight(runtime, "subadd"));
		this.setLeftbar("sale",0,0);
		
	}

}
