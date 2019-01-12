package com.tntxia.oa.main.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.http.client.ClientProtocolException;

import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class RegisterAppAction extends BaseAction {

	/**
	 * 
	 * 
	 * @param runtime
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 
	 */
	public Map<String,Object> execute(WebRuntime runtime) throws Exception {
		
		String contextPath = runtime.getRealPath("/");
		String name = runtime.getParam("name");
		String companyName = runtime.getParam("companyName");
		String companyNameEn = runtime.getParam("companyNameEn");
		
		ResourceBundle rb = ResourceBundle.getBundle("oa");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("center.host"));
		transfer.setPort(rb.getString("center.port"));
		transfer.setContextPath(rb.getString("center.context"));
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", name);
		param.put("path", contextPath);
		param.put("companyName", companyName);
		param.put("companyNameEn", companyNameEn);
		
		Map<String,Object> res = transfer.getMap("app!register", param);
		
		Map<String,Object> app = (Map<String,Object>)res.get("app");
		
		SystemCache.app = app;
		
		return this.success("app", app);
		
		
	}
	
}
