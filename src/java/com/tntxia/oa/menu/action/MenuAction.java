package com.tntxia.oa.menu.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.restrain.service.RestrainService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class MenuAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	private RestrainService restrainService = new RestrainService();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception {
		
		String userId = runtime.getSessionStr("userId");
		
		List<Integer> restrainIds = restrainService.listIds(userId);
		
		String ids = null;
		for(Integer id : restrainIds) {
			
			if(StringUtils.isEmpty(ids)) {
				ids = String.valueOf(id);
			}else {
				ids += ","+String.valueOf(id);
			}
		}
		
		String sql = "select * from menu where id in (select menu_id from restrain_menu where restrain_id in ("+ids+")) order by order_no";
		return dbManager.queryForList(sql, true);
	}

}
