package com.tntxia.oa.finance.service;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class FinanceAccountService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * ͨ����Ŀ���飬��ȡ��Ŀ����
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
