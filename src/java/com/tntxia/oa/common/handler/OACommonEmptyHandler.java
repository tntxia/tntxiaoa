package com.tntxia.oa.common.handler;

import com.tntxia.web.mvc.WebRuntime;

public class OACommonEmptyHandler extends OACommonHandler {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setRootValue("username", this.getUsername(runtime));

	}

}
