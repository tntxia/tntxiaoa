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
import com.tntxia.oa.user.service.UserService;
import com.tntxia.web.mvc.WebRuntime;

public class PrepaidAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	private UserService userService = new UserService();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		Map<String,Object> detail = dbManager.queryForMap("select * from lending_m where id = ?",new Object[] {id},true);
		return detail;
	}
	
	private String getAuditMan(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		String man = (String) detail.get("l_man");
		Map<String,Object> dept = userService.getUserDept(man);
		String deptname=(String) dept.get("deptname");
		String sqlddman = "select audit_man from WORK_FLOW where  dept=? and flow_type='prepaid'";
		String auditMan = dbManagerBack.getString(sqlddman, new Object[] { deptname });
		return auditMan;
	}
	
	private String getState(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		return (String) detail.get("l_spqk");
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		Map<String,Object> detail = dbManager.queryForMap("select * from lending_m where id = ?",new Object[] {id},true);
		String auditMan = getAuditMan(id);
		detail.put("auditMan", auditMan);
		return detail;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from lending_m order by id desc";
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from lending_m";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
		
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String number = NumberFactory.generateNumber("PPD");
		String l_man = runtime.getParam("l_man");
		String l_topic = runtime.getParam("l_topic");
		String l_date = DateUtil.getCurrentDateSimpleStr();
		String l_sqje = runtime.getParam("l_sqje");
		String l_purpose = runtime.getParam("l_purpose");
		String remarks = runtime.getParam("remarks");
		String expect_return_date  = runtime.getParam("expect_return_date");
		
		String sql="insert into lending_m(l_menber,l_man,l_topic,l_date,l_sqje,l_purpose,l_spqk,remarks,expect_return_date) values(?,?,?,?,?,?,'草拟',?,?)";
		dbManager.update(sql, new Object[] {number,l_man,l_topic,l_date,l_sqje,l_purpose,remarks,expect_return_date});
		return this.success();
	}
	
	public List<CommonToolbarItem> detailToolbar(WebRuntime runtime) throws Exception{
		
		List<CommonToolbarItem> toolbar = new ArrayList<CommonToolbarItem>();
		String id = runtime.getParam("id");
		String state = getState(id);
		if(StringUtils.equals(state, "草拟") || StringUtils.equals(state, "审批不通过")) {
			toolbar.add(new CommonToolbarItem("提交审批","sub"));
		}
		
		String username = this.getUsername(runtime);
		String auditMan = getAuditMan(id);
		if(StringUtils.equals(username, auditMan) && StringUtils.equals(state, "待审批")) {
			toolbar.add(new CommonToolbarItem("审批","toAudit"));
		}
		
		return toolbar;
	}
	
	public Map<String,Object> subToAudit(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String state = getState(id);
		if(!StringUtils.equals(state, "草拟") && !StringUtils.equals(state, "审批不通过")) {
			return this.errorMsg("费用状态异常，状态为："+state);
		}
		
		String strSQL="update  lending_m set l_spqk=? where id=?";
		dbManager.update(strSQL, new Object[] {"待审批",id});
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
		
		String strSQL="update  lending_m set l_spqk=?,l_spyj=? where id=?";
		
		dbManager.update(strSQL, new Object[] {state,opinion,id});
		
		return this.success();
	}

}
