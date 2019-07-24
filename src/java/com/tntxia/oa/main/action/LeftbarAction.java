package com.tntxia.oa.main.action;

import java.util.List;

import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.web.mvc.WebRuntime;

public class LeftbarAction extends CommonAction  {
	
	@SuppressWarnings("rawtypes")
	public List execute(WebRuntime runtime) throws Exception {
		String type = runtime.getParam("type");
		List list = SystemCache.leftbarMap.get(type);
		return list;
	}

}
