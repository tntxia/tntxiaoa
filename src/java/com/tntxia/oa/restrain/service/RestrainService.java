package com.tntxia.oa.restrain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class RestrainService extends CommonService{
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	@SuppressWarnings("rawtypes")
	public List listIds(String userId) throws Exception {
		String sql = "select role_id from user_role_rel where user_id = ?";
		List<Integer> ids = new ArrayList<Integer>();
		List list = dbManager.queryForList(sql,new Object[] {userId},	 true);
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer)map.get("role_id");
			ids.add(id);
		}
		return ids;
	}

}
