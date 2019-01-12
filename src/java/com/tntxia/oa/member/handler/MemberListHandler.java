package com.tntxia.oa.member.handler;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class MemberListHandler extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setRootValue("manadd", this.existRight(runtime, "manadd"));
		this.setLeftbar("member", 3, 0);
	}

}
