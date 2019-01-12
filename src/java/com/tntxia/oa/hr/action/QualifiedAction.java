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

public class QualifiedAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	private UserService userService = new UserService();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		Map<String,Object> detail = dbManager.queryForMap("select * from m_zzgl where id = ?",new Object[] {id},true);
		return detail;
	}
	
	private String getAuditMan(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		String man = (String) detail.get("l_man");
		String dept = userService.getUserDept(man);
		String sqlddman = "select audit_man from WORK_FLOW where  dept=? and flow_type='prepaid'";
		String auditMan = dbManagerBack.getString(sqlddman, new Object[] { dept });
		return auditMan;
	}
	
	private String getState(String id) throws Exception{
		Map<String,Object> detail = this.getDetail(id);
		return (String) detail.get("l_spqk");
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		Map<String,Object> detail = dbManager.queryForMap("select * from m_zzgl where id = ?",new Object[] {id},true);
		String auditMan = getAuditMan(id);
		detail.put("auditMan", auditMan);
		return detail;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from m_zzgl order by id desc";
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from m_zzgl";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
		
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String number = NumberFactory.generateNumber("Z");
		
		String sqlq="select count(*)  from  m_zzgl  where l_menber = ? order by in_no desc";
		
		if(dbManager.exist(sqlq,new Object[] {number})) {
			return this.errorMsg("编号重复，请稍候重试");
		}
		
		 String l_dg_date=runtime.getParam("l_dg_date");
		 String l_zz_date=runtime.getParam("l_zz_date");
		 String l_jgyy=runtime.getParam("l_jcyy");
		 
		 String l_date=DateUtil.getCurrentDateSimpleStr();
		 String remarks=runtime.getParam("remarks");
		 
		  
		
		    String dept = this.getDept(runtime);
		    String deptjb = this.getDeptjb(runtime);
		   	String role = this.getRole(runtime);
		   	String username1 = this.getUsername(runtime);
		  	
		 String sql="insert into m_zzgl(l_menber,l_man,l_dept,l_deptjb,l_role,l_dg_date,l_zz_date,l_date,l_jcyy,remarks,l_spqk) values(?,?,?,?,?,?,?,?,?,?,'草拟')";
		 
		dbManager.update(sql, new Object[] {number,username1,dept,deptjb,role,l_dg_date,l_zz_date,l_date,l_jgyy,remarks});
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
