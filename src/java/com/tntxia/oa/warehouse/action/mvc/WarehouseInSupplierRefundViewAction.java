package com.tntxia.oa.warehouse.action.mvc;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseInSupplierRefundViewAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from th_table_supplier where id = ?";
		this.putAllRootValue(dbManager.queryForMap(sql, new Object[] {id},true));
	}

}
