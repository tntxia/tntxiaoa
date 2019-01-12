package com.tntxia.oa.sale.dao;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseDao;

public class SampleDao extends BaseDao {
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getDetail(String id) throws Exception{
		String sql="select * from sample where id='"+id+"'";
		return dbManager.queryForMap(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getList(String id) throws Exception{
		 String strSQLpro = "select id,epro,cpro,num,unit,salejg,pricehb,rale_types,rale from sam_pro where ddid='"+id+"' order by id asc";
		 return dbManager.queryForList(strSQLpro, true);
	}

}
