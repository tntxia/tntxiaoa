package com.tntxia.oa.checkwork.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.UUIDUtils;

public class CheckWorkAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	/**
	 * 打卡
	 * @param request
	 * @param arg1
	 * @throws Exception
	 */
	public Map<String,Object> record(WebRuntime runtime) throws Exception {
		String today = DateUtil.getCurrentDateSimpleStr();
		String username = this.getUsername(runtime);
		String sql = "select * from work_clock where clock_date = ? and username = ?";
		Map<String, Object> record = dbManager.queryForMap(sql, new Object[] {today, username}, true);
		Date now = new Date(System.currentTimeMillis());
		String uuid;
		if (record != null) {
			uuid = (String) record.get("id");
			sql = "update work_clock set clock_out_time = ?, clock_times = clock_times + 1 where id = ?";
			dbManager.update(sql, new Object[] {now, uuid});
		} else {
			uuid = UUIDUtils.getUUID();
			sql = "insert into work_clock(id, username, clock_date, clock_in_time) values(?, ?, ?, ?)";
			dbManager.update(sql, new Object[] {uuid, username, today, now});
		}
		String detailId = UUIDUtils.getUUID();
		sql = "insert into work_clock_detail(id, clock_time, pid) values(?, ?,?)";
		dbManager.update(sql,new Object[]{detailId, now,uuid});
		return this.success();
	}
	
	/**
	 * 获取打卡记录
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		String username = this.getUsername(runtime);
		
		String sql = "select top "+pageBean.getTop()+" * from work_clock";
		
		String sqlWhere = " where 1 = 1";
		boolean viewAllAttendance = this.existRight(runtime, "view_all_attendance");
		List<Object> params = new ArrayList<Object>();
		if (!viewAllAttendance) {
			sqlWhere += " and username = ?";
			params.add(username);
		}
		
		String user = runtime.getParam("user");
		if (StringUtils.isNotEmpty(user)) {
			sqlWhere += " and username = ?";
			params.add(user);
		}
		
		String sdate = runtime.getParam("sdate");
		if (StringUtils.isNotEmpty(sdate)) {
			sqlWhere += " and clock_date >= ?";
			params.add(sdate);
		}
		
		String edate = runtime.getParam("edate");
		if (StringUtils.isNotEmpty(edate)) {
			sqlWhere += " and clock_date <= ?";
			params.add(edate);
		}
		
		String sqlOrderBy = " order by clock_date desc";
		
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);
		sql = "select count(*) from work_clock";
		int count = dbManager.getCount(sql + sqlWhere, params);
		
		return this.getPagingResult(list, pageBean, count);
	}

}
