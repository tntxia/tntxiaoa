package com.tntxia.oa.sale.handler;

import com.tntxia.oa.common.handler.OACommonHandler;

import com.tntxia.web.mvc.WebRuntime;

public class SampleNewHandler extends OACommonHandler {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		this.setRootValue("r_sam_add", this.existRight(runtime, "r_sam_add"));
	}

}
