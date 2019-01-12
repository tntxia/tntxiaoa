package com.tntxia.oa.current.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class CurrentService extends CommonService {
	
	@SuppressWarnings("rawtypes")
	private static List currentList;
	
	private DBManager dbManager = this.getDBManager();
	
	private void initCurrent() throws Exception {
		currentList = dbManager.queryForList("select * from hltable", true);
	}
	
	/**
	 * 根据货币编码，获取货币的汇率
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public BigDecimal getCurrentRate(String code) throws Exception {
		if(currentList==null) {
			this.initCurrent();
		}
		BigDecimal currrate;
		for(int i=0;i<currentList.size();i++) {
			Map map = (Map) currentList.get(i);
			
			String currname =(String) map.get("currname");
			currrate = (BigDecimal) map.get("currrate");
			
			if(currname.equals(code)) {
				return currrate;
			}
			
		}
		
		return null;
	}

}
