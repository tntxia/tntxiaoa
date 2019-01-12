package com.tntxia.oa.purchasing.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingRefundApprovedListHandler extends HandlerWithHeaderAndLeftbar{
	
	@Override
	public void init(WebRuntime runtime) {
		this.setLeftbar("purchasing", 2, 2);
		
	}

}
