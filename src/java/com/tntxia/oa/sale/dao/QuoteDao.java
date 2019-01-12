package com.tntxia.oa.sale.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseDao;
import com.tntxia.web.mvc.PageBean;

public class QuoteDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getDetail(String id) throws Exception{
		
		String sql = "select * from quote where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getList(String id) throws Exception{
		
		String sql = "select * from quoteproduct where quoteid = ? order by id";
		return dbManager.queryForList(sql, new Object[]{id}, true);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getList(String id,PageBean pageBean) throws Exception{
		
		String sql = "select top "+pageBean.getTop()+" * from quoteproduct where quoteid = ? order by id";
		List list = dbManager.queryForList(sql, new Object[]{id}, true);
		List rows = this.getRows(list, pageBean);
		return rows;
		
	}
	
	public BigDecimal getTotalPrice(String id) throws Exception{
		
		String sql = "select sum(price*quantity) from quoteproduct where quoteid = ?";
		return dbManager.queryForBigDecimal(sql, new Object[]{id});
		
	}

}
