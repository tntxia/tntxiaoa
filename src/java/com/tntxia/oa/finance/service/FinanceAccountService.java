package com.tntxia.oa.finance.service;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class FinanceAccountService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 通过科目详情，获取科目名称
	 * @param detail
	 * @return
	 * @throws Exception 
	 */
	public String getKmName(String detail) throws Exception {
		String sqlsmx="select * from km_mx where km_mx='"+detail+"'";
		Map<String,Object> rsskm = dbManager.queryForMap(sqlsmx,true);
		return (String) rsskm.get("km"); 
	}

}
