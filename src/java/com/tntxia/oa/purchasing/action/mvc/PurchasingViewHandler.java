package com.tntxia.oa.purchasing.action.mvc;

import com.tntxia.oa.common.handler.OACommonRedirectViewHandler;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.service.PurchasingLightService;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingViewHandler extends OACommonRedirectViewHandler {
	
	private PurchasingLightService service = new PurchasingLightService();

	@Override
	protected String init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		Purchasing purchasing = service.getPurchasingById(id);
		int status = purchasing.getStatus();
		if (status > 2) {
			return "audited";
		}
		return null;
	}
	
}
