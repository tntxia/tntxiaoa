package com.tntxia.oa.sale.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class SaleRefundDeletedListAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		setLeftbar("sale",6,3);
		
	}

}
