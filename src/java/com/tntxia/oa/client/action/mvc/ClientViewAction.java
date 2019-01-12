package com.tntxia.oa.client.action.mvc;


import com.tntxia.oa.client.service.ClientService;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ClientViewAction extends OACommonHandler {
	
	private ClientService service = new ClientService();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		
		this.setRootValue("xhmod", this.existRight(runtime, "xhmod"));
		this.setRootValue("xhdel", this.existRight(runtime, "xhdel"));
		this.putAllRootValue(service.getClient(id));
		
		
		
	}

}
