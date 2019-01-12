package com.tntxia.oa.common.handler;

import java.util.Map;

import com.tntxia.web.mvc.WebRuntime;

public class RightAndRoleHandler extends OACommonHandler{

	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		root.put("role", this.getRole(runtime));
		root.put("rightList", this.getUserRight(runtime));
		
	}

}
