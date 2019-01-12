package com.tntxia.oa.notice.handler;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.DateUtil;
import com.tntxia.web.mvc.WebRuntime;

public class PublicNewHandler extends OACommonHandler {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		this.setRootValue("username", this.getUsername(runtime));
		this.setRootValue("currentDate", DateUtil.getNowString("yyyy-MM-dd"));
		this.setRootValue("deptList",SystemCache.departmentList);
	}

}
