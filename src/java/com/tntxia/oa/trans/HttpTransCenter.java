package com.tntxia.oa.trans;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import com.tntxia.web.httptrans.HttpTransfer;

public class HttpTransCenter {
	
	private static Map<String,HttpTransfer> transMap = new HashMap<String,HttpTransfer>();
	
	public static HttpTransfer getHttpTrans(String contextPath){
		
		HttpTransfer trans = transMap.get(contextPath);
		
		if(trans==null){
			trans = new HttpTransfer();
			ResourceBundle rb = ResourceBundle.getBundle("http");
			
			String port = rb.getString("port");
			trans.setHost("localhost");
			trans.setPort(port);
			trans.setContextPath(contextPath);
			transMap.put(contextPath, trans);
		}
		
		return trans;
	}

}
