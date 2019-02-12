package com.tntxia.oa.sale.action;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class SaleReportAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager("oa_report");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listWorkPlanMonth(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from work_plan_month order by create_time desc";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from work_plan_month";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> addWorkPlanMonth(WebRuntime runtime, Map<String,Object> params) throws Exception {
		
		String number = NumberFactory.generateNumber("SWPM");
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String deptjb = this.getDeptjb(runtime);
		
		String year = (String) params.get("year");
		String month = (String) params.get("month");
		String total = (String) params.get("total");
		String client = (String) params.get("client");
		String product = (String) params.get("product");
		
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		
		String sql = "insert into work_plan_month(id,number,man,year,month,dept,deptjb,total,client,product,create_time) values(?,?,?,?,?,?,?,?,?,?,now())";
		dbManager.update(sql,new Object[] {id,number,username,year,month,dept,deptjb,total,client,product});
		
		List planList = (List) params.get("planList");
		for(int i=0;i<planList.size();i++) {
			Map plan = (Map) planList.get(i);
			String date_start = (String) plan.get("date_start");
			String date_end = (String) plan.get("date_end");
			String key_point = (String) plan.get("key_point");
			String remark = (String) plan.get("remark");
			sql = "insert into work_plan_month_plan_item(date_start,date_end,key_point,remark,pid) values(?,?,?,?,?)";
			dbManager.update(sql,new Object[] {date_start,date_end,key_point,remark,id});
		}
		
		List targetList = (List) params.get("targetList");
		for(int i=0;i<targetList.size();i++) {
			Map map = (Map) targetList.get(i);
			String target = (String) map.get("target");
			if(StringUtils.isEmpty(target)) {
				continue;
			}
			String date_before = (String) map.get("date_before");
			sql = "insert into work_plan_month_target_item(target,date_before,state,pid) values(?,?,?,?)";
			dbManager.update(sql,new Object[] {target,date_before,"not done",id});
		}
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> updateWorkPlanMonth(WebRuntime runtime, Map<String,Object> params) throws Exception {
		
		String id = (String) params.get("id");
		String total = (String) params.get("total");
		String process = runtime.getParam("process");
		String client = (String) params.get("client");
		String product = (String) params.get("product");
		
		String sql = "update work_plan_month set total = ?, process=?,client = ?,product= ? where id = ?";
		dbManager.update(sql,new Object[] {total,process,client,product,id});
		
		List planList = (List) params.get("planList");
		for(int i=0;i<planList.size();i++) {
			Map plan = (Map) planList.get(i);
			String planid = (String)plan.get("id");
			String date_start = (String) plan.get("date_start");
			String date_end = (String) plan.get("date_end");
			String key_point = (String) plan.get("key_point");
			String remark = (String) plan.get("remark");
			
			if(StringUtils.isEmpty(planid)) {
				sql = "insert into work_plan_month_plan_item(date_start,date_end,key_point,remark,pid) values(?,?,?,?,?)";
				dbManager.update(sql,new Object[] {date_start,date_end,key_point,remark,id});
			}else {
				sql = "update work_plan_month_plan_item set date_start=?,date_end=?,key_point=?,remark=? where id = ?";
				dbManager.update(sql,new Object[] {date_start,date_end,key_point,remark,planid});
			}
			
		}
		
		List targetList = (List) params.get("targetList");
		for(int i=0;i<targetList.size();i++) {
			Map map = (Map) targetList.get(i);
			String targetid = (String) map.get("id");
			String target = (String) map.get("target");
			if(StringUtils.isEmpty(target)) {
				continue;
			}
			String date_before = (String) map.get("date_before");
			if(StringUtils.isEmpty(targetid)) {
				sql = "insert into work_plan_month_target_item(target,date_before,state,pid) values(?,?,?,?)";
				dbManager.update(sql,new Object[] {target,date_before,"not done",id});
			}else {
				sql = "update work_plan_month_target_item set target=?,date_before=? where id = ?";
				dbManager.update(sql,new Object[] {target,date_before,targetid});
			}
			
		}
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> workPlanMonthDetail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from work_plan_month where id = ?";
		Map<String,Object> res = dbManager.queryForMap(sql,new Object[] {id}, true);
		
		List planItemList = dbManager.queryForList("select * from work_plan_month_plan_item where pid = ?", new Object[] {id}, true);
		res.put("planItemList", planItemList);
		
		List targetItemList = dbManager.queryForList("select * from work_plan_month_target_item where pid = ?", new Object[] {id}, true);
		res.put("targetItemList", targetItemList);
		
		return res;
	}
	
	public Map<String,Object> delWorkPlanMonth(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		String sql = "delete from  work_plan_month where id = ?";
		dbManager.update(sql,new Object[] {id});
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listWorkReportDaily(WebRuntime runtime) throws Exception {
		
		boolean isManager = this.isManager(runtime);
		PageBean pageBean = runtime.getPageBean();
		String sqlWhere = "where 1 = 1 ";
		if(!isManager) {
			String username = this.getUsername(runtime);
			sqlWhere += " and man = '" + username + "'";
		}
		String sql = "select top "+pageBean.getTop()+" * from work_report_daily "+sqlWhere+" order by id desc";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from work_report_daily "+sqlWhere;
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	public Map<String,Object> addWorkReportDaily(WebRuntime runtime) throws Exception {
		
		String number = NumberFactory.generateNumber("SWRD");
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String report_date = runtime.getParam("report_date");
		String report_desc = runtime.getParam("report_desc");
		
		String sql = "insert into work_report_daily(number,man,dept,report_date,report_desc,create_time) values(?,?,?,?,?,now())";
		dbManager.update(sql,new Object[] {number,username,dept,report_date,report_desc});
		
		return this.success();
	}
	
	public Map<String,Object> workReportDailyDetail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from work_report_daily where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
	}
	
	public Map<String,Object> updateWorkReportDaily(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String report_desc = runtime.getParam("report_desc");
		
		String sql = "update work_report_daily set report_desc = ? where id = ?";
		dbManager.update(sql,new Object[] {report_desc,id});
		
		
		
		return this.success();
	}
	
	public Map<String,Object> delWorkReportDaily(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		String sql = "delete from  work_report_daily where id = ?";
		dbManager.update(sql,new Object[] {id});
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listWorkReportWeekly(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from work_report_weekly";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from work_report_weekly";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	public Map<String,Object> addWorkReportWeekly(WebRuntime runtime) throws Exception {
		
		String number = NumberFactory.generateNumber("SWRW");
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String report_date_start = runtime.getParam("report_date_start");
		String report_date_end = runtime.getParam("report_date_end");
		String report_desc = runtime.getParam("report_desc");
		String total = runtime.getParam("total");
		
		String sql = "insert into work_report_weekly(number,man,dept,report_date_start,report_date_end,report_desc,total,create_time) values(?,?,?,?,?,?,?,now())";
		dbManager.update(sql,new Object[] {number,username,dept,report_date_start,report_date_end,report_desc,total});
		
		return this.success();
	}
	
	public Map<String,Object> workReportWeeklyDetail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from work_report_weekly where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
	}
	
	public Map<String,Object> updateWorkReportWeekly(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String total = runtime.getParam("total");
		String report_desc = runtime.getParam("report_desc");
		
		String sql = "update work_report_weekly set report_desc = ?,total=? where id = ?";
		dbManager.update(sql,new Object[] {report_desc,total,id});
		
		return this.success();
	}
	
	public Map<String,Object> delWorkReportWeekly(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		String sql = "delete from  work_report_weekly where id = ?";
		dbManager.update(sql,new Object[] {id});
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listWorkReportMonthly(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from work_report_monthly";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from work_report_monthly";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	public Map<String,Object> addWorkReportMonthly(WebRuntime runtime) throws Exception {
		
		String number = NumberFactory.generateNumber("SWRM");
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String report_date_start = runtime.getParam("report_date_start");
		String report_date_end = runtime.getParam("report_date_end");
		String report_desc = runtime.getParam("report_desc");
		String total = runtime.getParam("total");
		
		String sql = "insert into work_report_monthly(number,man,dept,report_date_start,report_date_end,report_desc,total,create_time) values(?,?,?,?,?,?,?,now())";
		dbManager.update(sql,new Object[] {number,username,dept,report_date_start,report_date_end,report_desc,total});
		
		return this.success();
	}
	
	public Map<String,Object> workReportMonthlyDetail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from work_report_monthly where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
	}
	
	public Map<String,Object> updateWorkReportMonthly(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String total = runtime.getParam("total");
		String report_desc = runtime.getParam("report_desc");
		
		String sql = "update work_report_monthly set report_desc = ?,total=? where id = ?";
		dbManager.update(sql,new Object[] {report_desc,total,id});
		
		return this.success();
	}
	
	public Map<String,Object> delWorkReportMonthly(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		String sql = "delete from  work_report_monthly where id = ?";
		dbManager.update(sql,new Object[] {id});
		
		return this.success();
	}


}
