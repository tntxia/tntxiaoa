package com.tntxia.oa.sale.handler;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;

import com.tntxia.web.mvc.WebRuntime;

public class SampleDraftListHandler extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setLeftbar("sale", 8, 0);
		this.setRootValue("r_sam_add", this.existRight(runtime, "r_sam_add"));
	}

}
