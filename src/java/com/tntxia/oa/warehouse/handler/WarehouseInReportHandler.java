package com.tntxia.oa.warehouse.handler;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseInReportHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String sqlWhere = "";
		
		boolean intViewRight = this.existRight(runtime, "intview");
		
		if(intViewRight){
			sqlWhere += " and deptjb like '"+this.getDeptjb(runtime)+"%'";
		}else{
			sqlWhere += " and man = '"+this.getUsername(runtime)+"'";
		}
		
		int count = dbManager.getCount("select count(*) from rkview where 1=1 "+sqlWhere);
		
		PageBean pageBean = runtime.getPageBean(20);
		
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" * from rkview where 1=1 "+sqlWhere,true);
		
		List rows = this.getRows(list, pageBean);
		
		this.setRootValue("rows", rows);
		
		this.setRootValue("pageBean",pageBean);
		

	}

}
