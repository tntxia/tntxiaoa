package com.tntxia.oa.sale.handler;

import java.util.Map;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SaleInquiryClientHandler extends OACommonHandler {

	@Override
	public void init(WebRuntime runtime) throws Exception {
		String coId = runtime.getParam("coId");
		Map<String,Object> root = this.getRoot();
		root.put("coId", coId);
		root.put("quoteadd", this.existRight(runtime, "r_cus_xj_add"));
	}

}
