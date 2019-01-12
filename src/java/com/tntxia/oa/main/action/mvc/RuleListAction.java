package com.tntxia.oa.main.action.mvc;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.web.mvc.WebRuntime;

public class RuleListAction extends HandlerWithHeader {
	
	private DBManager dbManager = this.getDBManager();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		boolean flfgadd = this.existRight(runtime, "flfgadd");
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
		
		String sql = "select * from rulestable  where 1=1 "+sqlWhere;
		
		List list = dbManager.queryForList(sql,true);
		
		this.setRootValue("list", list);
		
		this.setRootValue("flfgadd", flfgadd);

	}

}
