package com.tntxia.oa.notice.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;

import com.tntxia.web.mvc.WebRuntime;

public class NoticeAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 公告列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception{
		
		String currentDate = DateUtil.getCurrentDateSimpleStr();
		
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		
		String sql = "select * from pub_table where dept like '%"
				+ dept
				+ "%'  and  view_date>='"
				+ currentDate
				+ "'   and  states='审批通过'   or dept='全体员工'  and  view_date>='"
				+ currentDate
				+ "'  and  states='审批通过'   or   username='"
				+ username
				+ "'   and  view_date>='"
				+ currentDate
				+ "'   and  states='审批通过'     order by fvdate desc";
		return dbManager.queryForList(sql, true);
	}
	
	/**
	 * 增加公告
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String titel1 = runtime.getParam("title");
		String content1 = runtime.getParam("content");
		
		String username1 = this.getUsername(runtime);
		String fvdate1 = DateUtil.getCurrentDateSimpleStr();
		String dept = runtime.getParam("dept");
		int datet = 0;
		String datet1 = runtime.getParam("datet");
		if (datet1 != null)
			datet = Integer.parseInt(datet1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dd = format.parse(fvdate1);
		Calendar currentDate = Calendar.getInstance();

		currentDate.setTime(dd);
		currentDate.add(Calendar.DATE, datet);
		String vDate = format.format(currentDate.getTime());
		
		String states = "审批通过";

		String strSQL = "insert into pub_table(titel,content,username,fvdate,dept,datet,view_date,states) values('" + titel1 + "','"
				+ content1 + "','" + username1 + "','" + fvdate1 + "','"
				+ dept + "','" + datet + "','" + vDate + "','" + states + "')";
		dbManager.executeUpdate(strSQL);
		

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);

		

		return this.success();
	}
	
	private void updateFlow(String id,String ifsp,String dd_man) throws Exception{
		String sql = "update htsp set ifsp = ?,dd_man=? where id = ?";
		dbManager.update(sql, new Object[]{ifsp,dd_man,id});
	}
	
	/**
	 * 更新公告审批流程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> updateFlow(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String ifsp = runtime.getParam("ifsp");
		String dd_man = runtime.getParam("dd_man");
		
		updateFlow(id,ifsp,dd_man);
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getFlowList(WebRuntime runtime) throws Exception{
		int count = dbManager.queryForInt("select count(*) from htsp");
		List list = dbManager.queryForList("select * from htsp",true);
		return this.getPagingResult(list, runtime.getPageBean(20), count);
		
		
	}
	
}
