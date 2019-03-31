package com.tntxia.oa.sale.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class SaleSampleAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listToReturn(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean(50);
		
		String username = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean r_sam_view = this.existRight(runtime, "r_sam_view");
		
		String strSQL="select count(*) from sample";
		
		String sqlWhere;
		
		if(r_sam_view){
			sqlWhere = " where (state='部分入库' or   state='已发运'  or   state='已出库' or   state='待入库') and deptjb like '"+deptjb+"%'";
		}else
			sqlWhere = " where (state='部分入库' or   state='已发运'  or   state='已出库' or   state='待入库') and  man='"+username+"'";

		int count = dbManager.getCount(strSQL+sqlWhere);
		
		strSQL = "select top "+pageBean.getTop()+" * from sample";
		List list = dbManager.queryForList(strSQL+sqlWhere, true);
		
		return this.getPagingResult(list, pageBean, count);
	}
	
}
