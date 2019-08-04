package com.tntxia.oa.finance.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class WageAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top " + pageBean.getTop() + " * from wage_menber  order by wage_month desc ";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from wage_menber";
		int count = dbManager.getCount(sql);
		return this.getPagingResult(list, pageBean, count);
	}

}
