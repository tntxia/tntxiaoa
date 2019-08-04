package com.tntxia.oa.bank.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class BankAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();

	public Map<String,Object> list(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);
		String sql = "select top "+pageBean.getTop()+" * from bank";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.queryForInt("select count(*) from bank");

		return this.getPagingResult(list, pageBean, count);
	}
	
	public List listAll(WebRuntime runtime) throws Exception {
		String sql = "select * from sales_bank";
		return dbManager.queryForList(sql, true);
	}

}
