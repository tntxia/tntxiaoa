package com.tntxia.oa.sale.handler;


import java.util.Map;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;


import com.tntxia.web.mvc.WebRuntime;

public class SampleProNewHandler extends HandlerWithHeaderAndLeftbar {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql="select * from sample where id='"+id+"'";
		return dbManager.queryForMap(sql, true);
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String ddid=runtime.getParam("ddid");
		
		Map<String,Object> detail = this.getDetail(ddid);
		this.setLeftbar("sale", 8, 0);
		this.setRootValue("detail", detail);
		this.setRootValue("r_sam_add", this.existRight(runtime, "r_sam_add"));
		this.setRootValue("ddid", ddid);
		this.setRootValue("currentDate", DateUtil.getCurrentDateSimpleStr());
	}

}
