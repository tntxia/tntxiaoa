package com.tntxia.oa.purchasing.action.mvc;

import java.util.List;
import java.util.Map;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

@SuppressWarnings("rawtypes")
public class PurchasingNewAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map getMode(String id) throws Exception{
		String sql="select * from ht_mb where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	
	private List getPaywayPurchasing() throws Exception{
		String sql = "select * from payway_cg";
		return dbManager.queryForList(sql, true);
	}
	
	private List getCgsp() throws Exception{
		String sql = "select * from cgsp";
		return dbManager.queryForList(sql, true);
	}

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");
		
		setRootValue("mode", this.getMode(id));
		setRootValue("currentTypeList", SystemCache.currentList);
		setRootValue("payway", this.getPaywayPurchasing());
		setRootValue("cgsp", this.getCgsp());
		
	}

}
