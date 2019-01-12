package com.tntxia.oa.client.action.mvc;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ClientScoreAction extends OACommonHandler {
	


	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		this.setRootValue("id", id);
		
		
		
	}

}
