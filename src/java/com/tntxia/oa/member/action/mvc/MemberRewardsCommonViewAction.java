package com.tntxia.oa.member.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;

public abstract class MemberRewardsCommonViewAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getDetail(String id) throws Exception{
		String sql="select   *  from m_jcgl where id=?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}

	

}
