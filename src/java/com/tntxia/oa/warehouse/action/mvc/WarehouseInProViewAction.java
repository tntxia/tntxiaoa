package com.tntxia.oa.warehouse.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 查看入库产品信息
 * @author tntxia
 *
 */
public class WarehouseInProViewAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager("default");
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql = "select  * from rkhouse where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		
		String id = runtime.getParam("id");
		Map detail = this.getDetail(id);
		String role = this.getRole(runtime);
		this.putAllRootValue(detail);
		this.setRootValue("role", role);
		boolean hasPriceView = this.existRight(runtime, "proview_price");
		this.setRootValue("hasPriceView", hasPriceView);
		
		
		
	}
	
}
