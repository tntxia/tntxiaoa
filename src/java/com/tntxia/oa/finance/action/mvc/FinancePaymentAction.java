package com.tntxia.oa.finance.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FinancePaymentAction  extends HandlerWithHeader{
	
	@Override
	public void init(WebRuntime runtime) {
		
		Map<String,Object> root = this.getRoot();
		
		List leftbar = SystemCache.leftbarMap.get("finance");
		root.put("leftbar", leftbar);
		root.put("leftbarFirstSelected", 3);
		root.put("leftbarSecondSelected", 0);
		
		root.put("userList", SystemCache.userList);
	}

}
