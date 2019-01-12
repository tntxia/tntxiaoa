package com.tntxia.oa.purchasing.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingInquiryAction extends HandlerWithHeaderAndLeftbar {
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		setLeftbar("purchasing",0,2);
		
	}

}
