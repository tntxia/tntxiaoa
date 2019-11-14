package com.tntxia.oa.template.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;

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
	
	/**
	 * 获取合同模板的详情
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> detail(@Param("id") Integer id) throws Exception {
		String sql = "select * from ht_mb where id = ?";
		return this.success("data", dbManager.queryForMap(sql, new Object[] {id}, true));
	}

}
