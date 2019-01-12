package com.tntxia.oa.sale.action.mvc;

import java.util.HashMap;
import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;


public class SaleMessageAction extends HandlerWithHeaderAndLeftbar {

	public Map<String, Object> getMessageUser() throws Exception{
		String sql = "select * from messageuser ";
		return this.getDBManager().queryForMap(sql, true);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		this.setLeftbar("sale", -1, -1);

		Map<String, Object> user = this.getMessageUser();
		if(user==null){
			user = new HashMap();
		}
		this.setRootValue("user", user);
		java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate=simple.format(new java.util.Date());
        this.setRootValue("currentDate", currentDate);
		

	}

}
