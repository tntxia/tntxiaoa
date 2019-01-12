package com.tntxia.oa.checkwork.action.mvc;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class CheckWorkMainAction extends HandlerWithHeaderAndLeftbar {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setLeftbar("member", 5, 0);
	}

}
