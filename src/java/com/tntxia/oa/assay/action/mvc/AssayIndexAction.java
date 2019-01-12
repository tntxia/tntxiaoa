package com.tntxia.oa.assay.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class AssayIndexAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("assay", -1, -1);
		
	}

}
