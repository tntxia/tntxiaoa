package com.tntxia.oa.common.handler;

import java.util.Map;

import com.tntxia.web.mvc.WebRuntime;

public class HandlerWelcome extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {

		Map<String,Object> initParam = runtime.getInitParamMap();
		
		String leftbar = (String) initParam.get("leftbar");
		
		this.setLeftbar(leftbar, -1, -1);

	}

}
