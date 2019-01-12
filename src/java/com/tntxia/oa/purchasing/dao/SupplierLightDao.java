package com.tntxia.oa.purchasing.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.dbmanager.rowmapper.BeanRowMapper;
import com.tntxia.oa.purchasing.entity.Contact;
import com.tntxia.web.mvc.BaseDao;
import com.tntxia.web.mvc.PageBean;

public class SupplierLightDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();
	
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
	
	@SuppressWarnings("rawtypes")
	public List getContactList(String number) throws Exception{
		String sql = "select nameid id , name from qlinkman where co_number = ?";
		return dbManager.queryForList(sql, new Object[]{number}, new BeanRowMapper(Contact.class));
		
	}
	
}
