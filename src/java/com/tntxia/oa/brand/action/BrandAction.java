package com.tntxia.oa.brand.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 品牌管理
 * @author tntxia
 *
 */
public class BrandAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from profll where 1=1";
		
		String dict_type = runtime.getParam("dict_type");
		String dict_key = runtime.getParam("dict_key");
		String dict_value = runtime.getParam("dict_value");
		
		List<Object> params = new ArrayList<Object>();
		
		if(StringUtils.isNotEmpty(dict_type)){
			sql += " and dict_type like '%"+dict_type+"%' ";
		}
		
		if(StringUtils.isNotEmpty(dict_key)){
			sql += " and dict_key = ? ";
			params.add(dict_key);
		}
		
		if(StringUtils.isNotEmpty(dict_value)){
			sql += " and dict_value = ? ";
			params.add(dict_value);
		}
		
		return dbManager.queryForList(sql,params, true);
		
		
	}

}
