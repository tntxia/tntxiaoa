package com.tntxia.oa.checkwork.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.checkwork.service.CheckworkService;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class CheckWorkAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager();
	
	private CheckworkService service = new CheckworkService();
	
	/**
	 * 打卡
	 * @param request
	 * @param arg1
	 * @throws Exception
	 */
	public Map<String,Object> record(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String username = this.getUsername(request);
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into tntxiaoa_checkwork_record(check_time,user_name) values(?,?)";
		dbManager.update(sql,new Object[]{sdf.format(now),username});
		
		return this.success();
	}
	
	/**
	 * 获取今天的打卡记录
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getTodayCheckworkRecord(WebRuntime runtime) throws Exception {
		String username = this.getUsername(runtime);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todayStart = DateUtil.getTodayStart();
		Date tomorrowStart = DateUtil.getTomorrowStart();
		return service.searchCheckworkRecord(username,sdf.format(todayStart),sdf.format(tomorrowStart));
	}
	
	/**
	 * 查询考勤记录
	 * @param runtime 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listCheckRecord(WebRuntime runtime) throws Exception {
		
		String sqlWhere = "where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		
		String name = runtime.getParam("name");
		if(StringUtils.isNotEmpty(name)) {
			sqlWhere += " and user_name = ?";
			params.add(name);
		}
		
		String sdate = runtime.getParam("sdate");
		if(StringUtils.isNotEmpty(sdate)) {
			sqlWhere += " and check_time >= ?";
			params.add(sdate);
		}
		
		String edate = runtime.getParam("edate");
		if(StringUtils.isNotEmpty(edate)) {
			sqlWhere += " and check_time < ?";
			params.add(DateUtil.getNextDate(edate));
		}
		
		String sql = "select * from tntxiaoa_checkwork_record "+sqlWhere+" order by id desc";
		List list = dbManager.queryForList(sql,params, true);
		int count = dbManager.getCount("select count(*) from tntxiaoa_checkwork_record "+sqlWhere,params);
		return this.getPagingResult(list, runtime.getPageBean(),count);
		
	}

}
