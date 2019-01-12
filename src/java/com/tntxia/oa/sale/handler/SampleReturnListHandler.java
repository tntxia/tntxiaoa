package com.tntxia.oa.sale.handler;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;

import com.tntxia.web.mvc.WebRuntime;

public class SampleReturnListHandler extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setLeftbar("sale", 8, 4);
	}

}
