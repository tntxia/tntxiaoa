package com.tntxia.oa.template.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class TemplateAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 获取采购合同的列表
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception {
		String sql = "select * from ht_mb";
		
		List<Object> params = new ArrayList<Object>();
		
		String type = runtime.getParam("type");
		if(StringUtils.isNotEmpty(type)) {
			sql += " where template_type = ?";
			params.add(type);
		}
		
		return dbManager.queryForList(sql, params.toArray(), true);
	}

}
