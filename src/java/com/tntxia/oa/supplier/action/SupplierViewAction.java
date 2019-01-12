package com.tntxia.oa.supplier.action;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SupplierViewAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");
		
		
		
		String sql = "select * from supplier where id = ?" ;
		
		Map<String,Object> supplier = dbManager.queryForMap(sql, new Object[] {id},true);
		this.putAllRootValue(supplier);
		
		String co_number = (String) supplier.get("co_number");
		
		String sql2 = "select top 10 * from qlinkman where co_number=?";
		
		this.setRootValue("contactList", dbManager.queryForList(sql2, new Object[] {co_number},true));
		this.setRootValue("supmod", this.existRight(runtime, "supmod"));
		this.setRootValue("supdel", this.existRight(runtime, "supdel"));
		
	}

}
