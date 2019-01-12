package com.tntxia.oa.hr.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class ActivityAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();

	/**
	 * 增加一个活动
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> add(WebRuntime runtime) throws Exception {

		String actypes1 = runtime.getParam("actypes");
		String coname1 = runtime.getParam("coname");
		String coaddr = runtime.getParam("coaddr");
		String coman = runtime.getParam("coman");
		String cotel = runtime.getParam("cotel");
		String cofax = runtime.getParam("cofax");
		String conet = runtime.getParam("conet");
		String coemail = runtime.getParam("coemail");
		String name1 = runtime.getParam("name");
		String clewtime1 = runtime.getParam("clewtime");
		String ytime1 = runtime.getParam("ytime");
		String iftx1 = runtime.getParam("iftx");
		String txtime1 = runtime.getParam("txtime");
		String state1 = runtime.getParam("state");
		String pri1 = runtime.getParam("pri");
		String descripta1 = runtime.getParam("descripta");
		String remark1 = runtime.getParam("remark");
		String deptjb = runtime.getParam("deptjb");

		String strSQL = "insert into activity(actypes,coname,coaddr,coman,cotel,cofax,conet,coemail,name,clewtime,ytime,iftx,txtime,state,pri,descripta,remark,deptjb) values('" + actypes1 + "','" + coname1 + "','" + coaddr + "','" + coman
				+ "','" + cotel + "','" + cofax + "','" + conet + "','" + coemail + "','" + name1 + "','" + clewtime1
				+ "','" + ytime1 + "','" + iftx1 + "','" + txtime1 + "','" + state1 + "','" + pri1 + "','" + descripta1
				+ "','" + remark1 + "','" + deptjb + "')";
		dbManager.executeUpdate(strSQL);

		return this.success();
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> list(WebRuntime runtime) throws Exception {

		String sql = "select * from activity order by id desc";
		List list = dbManager.queryForList(sql, true);

		sql = "select count(*) from activity";
		int count = dbManager.getCount(sql);

		return this.getPagingResult(list, runtime, count);

	}
	
	public List getTypes(WebRuntime runtime) throws Exception {
		
		String sql = "select * from activity_type";
		
		return dbManager.queryForList(sql, true);
		
	}

}
