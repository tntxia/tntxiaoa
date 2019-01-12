package com.tntxia.oa.purchasing.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingRefundDraftListHandler extends HandlerWithHeader{
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) {
		
		Map<String,Object> root = this.getRoot();
		
		List leftbar = SystemCache.leftbarMap.get("purchasing");
		
		root.put("leftbar", leftbar);
		root.put("leftbarFirstSelected", 2);
		root.put("leftbarSecondSelected", 0);
		
		
	}

}
