package com.tntxia.oa.client.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class ClientIndexAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		this.setLeftbar("sale", 4, 1);
	}

}
