package com.tntxia.oa.warehouse.action.mvc;

import java.util.HashMap;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.service.PurchasingLightService;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseInNewAction extends OACommonHandler{
	
	private PurchasingLightService service = new PurchasingLightService();
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String purchasingId = runtime.getParam("purchasingId");
		if(purchasingId!=null){
			Purchasing purchasing = service.getPurchasingById(purchasingId);
			this.setRootValue("purchasing", purchasing);
		}else{
			this.setRootValue("purchasing", new HashMap<String, Object>());
		}
		
		
	}
	
}
