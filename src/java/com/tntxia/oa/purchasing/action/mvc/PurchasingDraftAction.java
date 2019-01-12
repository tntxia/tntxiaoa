package com.tntxia.oa.purchasing.action.mvc;

import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.RequestUtils;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingDraftAction extends HandlerWithHeaderAndLeftbar{
	
	
	@Override
	public void init(WebRuntime runtime) {
		
		Map<String,Object> root = this.getRoot();
		
		this.setLeftbar("purchasing", 1, 0);
		
		root.put("suppliers", SystemCache.suppliers);
		
		boolean canAdd = RequestUtils.existRight(this.getRuntime(), "cgadd");
		root.put("canAdd", canAdd);
		
	}

}
