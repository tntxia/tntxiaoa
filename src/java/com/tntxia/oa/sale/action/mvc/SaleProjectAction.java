package com.tntxia.oa.sale.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class SaleProjectAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception{
		
		this.setLeftbar( "sale",2,0);
		
	}

}
