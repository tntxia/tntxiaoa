package com.tntxia.oa.appeal.handler;

import java.util.Map;

import com.tntxia.common.date.DateUtil;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class AppealNewHandler extends OACommonHandler {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		root.put("username", this.getUsername(runtime));
		root.put("currentDate", DateUtil.getCurrentDateSimpleStr());
	}

}
