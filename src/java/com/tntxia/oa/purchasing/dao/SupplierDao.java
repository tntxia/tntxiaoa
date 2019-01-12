package com.tntxia.oa.purchasing.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.PageBean;

public class SupplierDao {
	
	private DBManager dbManager;

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	@SuppressWarnings("rawtypes")
	public List list(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		int top = pageBean.getTop();
		String sql = "select top "+top+" * from supplier ";
		
		String sqlWhere = "where 1=1 ";
		
		String coname = (String) param.get("coname");
		if(StringUtils.isNotEmpty(coname)){
			sqlWhere += " and coname like '%"+coname+"%'";
		}
		
		String country = (String) param.get("country");
		if(StringUtils.isNotEmpty(country)){
			sqlWhere += " and country like '%"+country+"%'";
		}
		
		String province = (String) param.get("province");
		if(StringUtils.isNotEmpty(province)){
			sqlWhere += " and country like '%"+province+"%'";
		}
		
		String scale = (String) param.get("province");
		if(StringUtils.isNotEmpty(scale)){
			sqlWhere += " and scale like '%"+scale+"%'";
		}
		
		return dbManager.queryForList(sql+sqlWhere, true);
		
	}
	

	public int getTotal(Map<String,Object> param) throws Exception{
		
		String sql = "select count(*) from supplier ";
		
		String sqlWhere = "where 1=1 ";
		String coname = (String) param.get("coname");
		if(StringUtils.isNotEmpty(coname)){
			sqlWhere += " and coname like '%"+coname+"%'";
		}
		
		String country = (String) param.get("country");
		if(StringUtils.isNotEmpty(country)){
			sqlWhere += " and country like '%"+country+"%'";
		}
		
		String province = (String) param.get("province");
		if(StringUtils.isNotEmpty(province)){
			sqlWhere += " and country like '%"+province+"%'";
		}
		
		String scale = (String) param.get("province");
		if(StringUtils.isNotEmpty(scale)){
			sqlWhere += " and scale like '%"+scale+"%'";
		}
		
		return dbManager.getCount(sql+sqlWhere);
		
	}
	
	public Map<String,Object> getPf(String number) throws Exception{
		String sql = "select avg(rfq) rfq,avg(bj) bj,avg(ch) ch,avg(fh) fh,avg(th) th from pf_supplier where co_number = ?";
		return dbManager.queryForMap(sql, new Object[]{number} ,true);
	}
	
	

}
