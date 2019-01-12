package com.tntxia.oa.common.handler;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.util.RequestUtils;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 所有有头部的页面的处理器
 * @author tntxia
 *
 */
public abstract class HandlerWithHeader extends OACommonHandler{
	
	private DBManager systemDbManager = this.getDBManager("oa_back");
	
	private WebRuntime runtime;
	
	public WebRuntime getRuntime() {
		return runtime;
	}

	@SuppressWarnings("rawtypes")
	public List getMenus(Integer restrainId) throws Exception{
		String sql = "select m.* from restrain_menu rm inner join menu m on rm.menu_id = m.id where restrain_id = ? order by m.order_no";
		return systemDbManager.queryForList(sql, new Object[]{restrainId}, true);
	}
	
	public HandlerWithHeader(){
		
		
	}
	
	public Map<String,Object> execute(WebRuntime runtime) throws Exception{
		
		this.runtime = runtime;
		Integer restrainId = RequestUtils.getRestrainId(runtime);
		Map<String,Object> root = this.getRoot();
		root.put("username", this.getUsername(runtime));
		root.put("menus", this.getMenus(restrainId));
		init(runtime);
		return this.getRoot();
	}
	

}
