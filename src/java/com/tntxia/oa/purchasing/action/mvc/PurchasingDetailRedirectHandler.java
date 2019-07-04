package com.tntxia.oa.purchasing.action.mvc;

import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.service.PurchasingLightService;
import com.tntxia.oa.util.WebUtils;
import com.tntxia.web.mvc.RedirectViewHandler;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingDetailRedirectHandler extends RedirectViewHandler {
	
	private PurchasingLightService purchasingService = new PurchasingLightService();

	@Override
	protected String init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Purchasing purchasing = purchasingService.getPurchasingById(id);
		
		int status = purchasing.getStatus();
		// 已审批
		if(status>2) {
			return "audited";
		// 待审批
		}else if(status==1) {
			String spman = purchasing.getFirstApprover();
			String username = WebUtils.getUsername(runtime);
			if(spman.trim().equals(username.trim())) {
				return "auditing_operator";
			}else {
				return "auditing_not_operator";
			}
		// 待复审
		}else if(status==2) {
			String fspman = purchasing.getSecondApprover();
			fspman = fspman.trim();
			String username = WebUtils.getUsername(runtime);
			username = username.trim();
			if(fspman.equals(username)) {
				return "auditing_second_operator";
			}else {
				return "auditing_second_not_operator";
			}
		}else {
			return "draft";
		}
		
	}

}
