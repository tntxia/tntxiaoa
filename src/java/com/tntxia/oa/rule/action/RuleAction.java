package com.tntxia.oa.rule.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class RuleAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 新增订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		
		boolean flfgview = this.existRight(runtime, "flfgview");

		String sqlWhere = "";
		
		if (flfgview) {
			String deptjb = this.getDeptjb(runtime);
			sqlWhere += " and (deptjb like '"
					+ deptjb + "%' or dept='全体员工')";
		} else {
			String dept = this.getDept(runtime);
			sqlWhere = "and (dept='" + dept
					+ "' or dept='全体员工')";
		}
		
		String strSQL = "select count(*) from rulestable where 1=1  "+sqlWhere;
		
		int count = dbManager.getCount(strSQL);
		
		String sql = "select top "+pageBean.getTop()+" * from rulestable  where 1=1 "+sqlWhere;
		
		List list = dbManager.queryForList(sql,true);

		return this.getPagingResult(list, pageBean, count);
		

	}

}
