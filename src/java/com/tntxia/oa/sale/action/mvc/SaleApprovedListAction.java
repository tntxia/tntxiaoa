package com.tntxia.oa.sale.action.mvc;

import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class SaleApprovedListAction extends HandlerWithHeaderAndLeftbar {
	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		
		Map<String,Object> root = this.getRoot();
		this.setLeftbar("sale", 0, 2);
		root.put("depts", SystemCache.departmentList);
		root.put("users", SystemCache.saleUserList);
		root.put("hasViewTopRight",
				existRight(runtime, "sale_view_top_50"));
	}

}
