package com.tntxia.oa.client.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.annotation.Param;

public class ContactAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getClientDetail(String id) throws Exception{
		return dbManager.queryForMap("select * from client where clientid = ?",new Object[]{id}, true);
	}
	
	public Map<String,Object> detail(@Param("id") String id) throws Exception {
		
		String sql = "select * from linkman where nameid = ?";
		Map<String,Object> res = dbManager.queryForMap(sql, new Object[] {id}, true);
		return res;
	}
	
	public Map<String,Object> del(@Param("id") String id) throws Exception {
		String sql = "delete from linkman where nameid = ?";;
		dbManager.update(sql, new Object[]{id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(@Param("pid") String pid, PageBean pageBean) throws Exception {
		Map<String,Object> client = getClientDetail(pid);
		String co_number = (String) client.get("co_number");
		String sql = "select top " + pageBean.getTop() + " * from linkman ";
		String sqlWhere = " where co_number = ?";
		List list = dbManager.queryForList(sql+ sqlWhere, new Object[] {co_number}, true);
		sql = "select count(*) from linkman";
		int count = dbManager.getCount(sql + sqlWhere, new Object[] {co_number});
		return this.getPagingResult(list, pageBean, count);
	}
	
}
