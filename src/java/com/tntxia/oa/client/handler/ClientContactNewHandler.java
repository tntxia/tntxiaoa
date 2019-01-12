package com.tntxia.oa.client.handler;

import java.util.Map;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ClientContactNewHandler extends OACommonHandler {
	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		String coId = runtime.getParam("coId");
		root.put("coId", coId);

	}

}
