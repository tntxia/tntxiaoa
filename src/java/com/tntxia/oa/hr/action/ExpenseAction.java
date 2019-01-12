package com.tntxia.oa.hr.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.CommonToolbarItem;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class ExpenseAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();
	
	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	private Map<String,Object> getDetail(String id) throws Exception{
		Map<String,Object> detail = dbManager.queryForMap("select * from lending where id = ?",new Object[] {id},true);
		
		return detail;
	}
	
	private String getAuditMan(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		String dept = (String) detail.get("l_dept");
		String sqlddman = "select audit_man from WORK_FLOW where  dept=? and flow_type='expense'";
		String auditMan = dbManagerBack.getString(sqlddman, new Object[] { dept });
		return auditMan;
	}
	
	private String getState(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		return (String) detail.get("l_spqk");
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		Map<String,Object> detail = dbManager.queryForMap("select * from lending where id = ?",new Object[] {id},true);
		String auditMan = getAuditMan(id);
		detail.put("auditMan", auditMan);
		return detail;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from lending order by id desc";
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from lending";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
		
	}

	/**
	 * 增加费用
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> add(WebRuntime runtime) throws Exception {

		String expenseNumber = NumberFactory.generateNumber("EXP");

		String l_topic = runtime.getParam("l_topic");
		String l_date = DateUtil.getCurrentDateSimpleStr();
		String l_sqje = runtime.getParam("l_sqje");

		String l_purpose = runtime.getParam("l_purpose");

		String l_coname = runtime.getParam("l_coname");
		String remarks = runtime.getParam("remarks");

		String dept = runtime.getParam("l_dept");
		String deptjb = this.getDeptjb(runtime);
		
		String man = runtime.getParam("l_man");

		String username1 = this.getUsername(runtime);

		String strSQL = "insert into lending(l_menber,l_man,l_topic,l_dept,l_deptjb,l_date,l_coname,l_sqje,l_purpose,l_spqk,remarks,man) values(?,?,?,?,?,?,?,?,?,'草拟',?,?)";
		dbManager.update(strSQL, new Object[] { expenseNumber, man, l_topic, dept, deptjb, l_date, l_coname,
				l_sqje, l_purpose, remarks,username1 });

		return this.success();

	}
	
	/**
	 * 增加费用
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> update(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String l_topic = runtime.getParam("l_topic");
		String l_sqje = runtime.getParam("l_sqje");
		String l_purpose = runtime.getParam("l_purpose");
		String remarks = runtime.getParam("remarks");
		String dept = runtime.getParam("l_dept");
		String man = runtime.getParam("l_man");

		String strSQL = "update lending set l_man=?,l_topic=?,l_dept=?,l_sqje=?,l_purpose=?,remarks=? where id = ?";
		dbManager.update(strSQL, new Object[] { man, l_topic, dept, 
				l_sqje, l_purpose, remarks,id });

		return this.success();

	}
	
	@SuppressWarnings("rawtypes")
	public List listAccount(WebRuntime runtime) throws Exception{
		String sql = "select * from km_f";
		return dbManager.queryForList(sql, true);
	}
	
	public Map<String,Object> subToAudit(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String state = getState(id);
		if(!StringUtils.equals(state, "草拟") && !StringUtils.equals(state, "审批不通过")) {
			return this.errorMsg("费用状态异常，状态为："+state);
		}
		
		String auditMan = this.getAuditMan(id);
		String strSQL="update  lending set l_spqk=?,l_spman=? where id=?";
		dbManager.update(strSQL, new Object[] {"待审批",auditMan,id});
		
		return this.success();
	}
	
	public Map<String,Object> audit(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String username = this.getUsername(runtime);
		String auditMan = getAuditMan(id);
		if(!StringUtils.equals(username, auditMan)) {
			return this.errorMsg("你不是审批人员！");
		}
		
		String state = getState(id);
		if(!StringUtils.equals(state, "待审批")) {
			return this.errorMsg("费用状态异常，状态为："+state);
		}
		
		state = runtime.getParam("result");
		String opinion = runtime.getParam("opinion");
		
		String strSQL="update  lending set l_spqk=?,l_spyj=? where id=?";
		
		dbManager.update(strSQL, new Object[] {state,opinion,id});
		
		return this.success();
	}
	
	public List<CommonToolbarItem> detailToolbar(WebRuntime runtime) throws Exception{
		List<CommonToolbarItem> toolbar = new ArrayList<CommonToolbarItem>();
		String id = runtime.getParam("id");
		String state = getState(id);
		if(StringUtils.equals(state, "草拟") || StringUtils.equals(state, "审批不通过")) {
			toolbar.add(new CommonToolbarItem("编辑","edit"));
			toolbar.add(new CommonToolbarItem("提交审批","sub"));
		}
		
		String username = this.getUsername(runtime);
		String auditMan = getAuditMan(id);
		if(StringUtils.equals(username, auditMan) && StringUtils.equals(state, "待审批")) {
			toolbar.add(new CommonToolbarItem("审批","toAudit"));
		}
		
		return toolbar;
		
	}

}
