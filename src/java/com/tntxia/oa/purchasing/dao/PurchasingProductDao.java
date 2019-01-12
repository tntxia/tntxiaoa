package com.tntxia.oa.purchasing.dao;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.util.DBManagerUtil;

public class PurchasingProductDao {
	
	private DBManager dbManager = DBManagerUtil.getDBManager();
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getProductByPurchasingId(String purchasingId) throws Exception{
		
		
		String sql="select * from cgpro where ddid=? order by id asc";
		return dbManager.queryForList(sql, new Object[]{purchasingId},true);
		
	}
	

}
