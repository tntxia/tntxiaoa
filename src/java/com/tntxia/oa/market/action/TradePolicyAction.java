package com.tntxia.oa.market.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class TradePolicyAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from trade_policy  order by  id desc";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from trade_policy";
		int count = dbManager.getCount(sql);
		return this.getPagingResult(list, pageBean, count);		
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception {
		
		 String bring_date=(String)runtime.getParam("bring_date");
		 String w_man=(String)runtime.getParam("man");
		 String w_dept=(String)runtime.getParam("dept");
		 String  w_deptjb=(String)runtime.getParam("deptjb");
		 String  dis_date=(String)runtime.getParam("dis_date");
		 String  trade_name=(String)runtime.getParam("trade_name");
		 String  trade_subject=(String)runtime.getParam("trade_subject");
		 String trade_content=(String)runtime.getParam("trade_content");
		 String trade_extent=(String)runtime.getParam("trade_extent");
		 String trade_source=(String)runtime.getParam("trade_source");
		 String number = NumberFactory.generateNumber("TP");
		 String w_spman=(String)runtime.getParam("spman");
		 String w_datetime=(String)runtime.getParam("rg_datetime");

		 String strSQL="insert into trade_policy(NUMBER,RG_DATETIME,MAN,DEPT,DEPTJB,TRADE_SUBJECT,TRADE_NAME,TRADE_CONTENT,BRING_DATE,DIS_DATE,TRADE_EXTENT,TRADE_SOURCE,SPMAN,STATES,SPYJ) values(?,'" + w_datetime +"','"+w_man + "','" + w_dept +"','" + w_deptjb +"','"+trade_subject+"','"+trade_name+"','"+trade_content+"','"+bring_date+"','"+dis_date+"','"+trade_extent+"','"+trade_source+"','"+w_spman+"','未提交','')";
		 dbManager.executeUpdate(strSQL, new Object[] {number});
		 return this.success();
	}
	
}
