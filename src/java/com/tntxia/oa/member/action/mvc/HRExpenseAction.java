package com.tntxia.oa.member.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class HRExpenseAction extends HandlerWithHeaderAndLeftbar {
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		this.setLeftbar("member", 0, 0);
		Boolean fyadd=this.existRight(runtime, "fyadd");
		this.setRootValue("fyadd", fyadd);
	}

}
