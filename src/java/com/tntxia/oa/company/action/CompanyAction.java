package com.tntxia.oa.company.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class CompanyAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean();
		
		String sql = "select top "+pageBean.getTop()+" * from company";
		
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from company";
		
		int count = dbManager.queryForInt(sql);
		
		
		return this.getPagingResult(list, runtime, count);
	}
	
	public List listAll(WebRuntime runtime) throws Exception{
		String sql = "select * from company";
		return dbManager.queryForList(sql, true);
	}

}
