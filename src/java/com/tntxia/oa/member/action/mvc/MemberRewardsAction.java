package com.tntxia.oa.member.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class MemberRewardsAction extends HandlerWithHeaderAndLeftbar {
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setRootValue("fyadd", this.existRight(runtime, "r_jcsq_add"));
		
		this.setLeftbar("member", 4, 1);
	}
	
}
