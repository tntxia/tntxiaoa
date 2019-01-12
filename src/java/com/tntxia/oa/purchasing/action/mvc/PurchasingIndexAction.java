package com.tntxia.oa.purchasing.action.mvc;

import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingIndexAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		Map<String,Object> root = this.getRoot();
		
		
		
		this.setLeftbar("purchasing",-1,-1);
		
		// 用户登陆信息
		String role = this.getRole(runtime);
		String loginTime = runtime.getSessionStr("currentDate");
		root.put("role", role);
		root.put("loginTime", loginTime);
		
		
	}

}
