package com.tntxia.oa.purchasing.dao;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.util.DBManagerUtil;

public class TrademarkDao {
	
	private DBManager dbManager = DBManagerUtil.getDBManager();
	
	/**
	 * ��ȡ���е�Ʒ����Ϣ
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List trademarkList() throws Exception {
		List res = dbManager.queryForList("select * from profll", true);
		return res;
	}

}
