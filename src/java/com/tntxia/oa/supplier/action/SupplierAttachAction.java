package com.tntxia.oa.supplier.action;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SupplierAttachAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String supplierId = runtime.getParam("id");   // π©”¶…Ãid
		String sql = "select id,filename,filepath,remark from supplier_attachment where supplierid = '"+supplierId+"'";
		this.setRootValue("id", supplierId);
		this.setRootValue("attachList", dbManager.queryForList(sql, true));
		
	}

}
