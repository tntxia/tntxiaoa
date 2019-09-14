package com.tntxia.oa.appeal.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.datepower.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class AppealAction extends CommonDoAction {
	
	DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	private List getList(PageBean pageBean,String state) throws Exception{
		String sqlWhere = "";
		List<Object> params = new ArrayList<Object>();
		if(state!=null){
			sqlWhere = "where state=?";
			params.add(state);
		}
		String sql = "select top "+pageBean.getTop()+" * from appeal "+sqlWhere+" order by aid  desc";
		return dbManager.queryForList(sql, params.toArray(),true);
	}
	
	private int getCount(String state) throws Exception{
		String sqlWhere = "";
		List<Object> params = new ArrayList<Object>();
		if(state!=null){
			sqlWhere = "where state=?";
			params.add(state);
		}
		String sql = "select count(*) from appeal "+sqlWhere;
		return dbManager.getCount(sql,params.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	private List getList(PageBean pageBean) throws Exception{
		return getList(pageBean,null);
	}
	
	private int getCount() throws Exception{
		return getCount(null);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		List list = this.getList(pageBean,"未办理");
		int count = this.getCount("未办理");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listApproving(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		List list = this.getList(pageBean,"办理中");
		int count = this.getCount("办理中");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listApproved(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		List list = this.getList(pageBean,"领导批示");
		int count = this.getCount("领导批示");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listFinish(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		List list = this.getList(pageBean,"办理完毕");
		int count = this.getCount("办理完毕");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listAll(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		List list = this.getList(pageBean);
		int count = this.getCount();
		return this.getPagingResult(list, pageBean, count);
	}
	
	public Map<String,Object> sendReflect(WebRuntime runtime) throws Exception{
		String aid = runtime.getParam("aid");
		String cml = runtime.getParam("cml");
		String cfk = runtime.getParam("cfk");
		String strSQL="update appeal set cml=?,cfk=? where aid=?";
		dbManager.update(strSQL,new Object[]{cml,cfk,aid});
		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String aid = runtime.getParam("id");
		String strSQL="delete from appeal where aid=?";
		dbManager.update(strSQL,new Object[]{aid});
		return this.success();
	}
	
	/**
	 * 增加投诉
	 * @throws Exception 
	 */
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String number = NumberFactory.generateNumber("P");
		
		 String appealfl1=runtime.getParam("appealfl");
		 String appealdj1=runtime.getParam("appealdj");
		 
		 String creatdt1=runtime.getParam("creatdt");
		 String enddt1=runtime.getParam("enddt");
		 String receipt1=runtime.getParam("receipt");
		 String groupnumber1=runtime.getParam("groupnumber");
		 String ordernumber1=runtime.getParam("ordernumber");
		 String contract1=runtime.getParam("contract");
		 String epro1=runtime.getParam("epro");
		 String cpro1=runtime.getParam("cpro");
		 String number1=runtime.getParam("number");
		 String unit1=runtime.getParam("unit");
		 String standard1=runtime.getParam("standard");
		 String transport1=runtime.getParam("transport");
		 String supplier1=runtime.getParam("supplier");
		 String sdate1=runtime.getParam("sdate");
		 String customer1=runtime.getParam("customer");
		 String cdate1=runtime.getParam("cdate");
		 String appeallr1=runtime.getParam("appeallr");
		 String checknr1=runtime.getParam("checknr");
		 String checkman1=this.getUsername(runtime);
		 String checkdate1=DateUtil.getCurrentDateSimpleStr();
		 String clfa1=runtime.getParam("clfa");
		 String clfaman1=runtime.getParam("clfaman");
		 String clfadate1=runtime.getParam("clfadate");
		 String feedback1=runtime.getParam("feedback");
		 String feedbackman1=runtime.getParam("feedbackman");
		 String feedbackdate1=runtime.getParam("feedbackdate");
		  
		 String remarks1=runtime.getParam("remarks");
		 String strSQL="insert into appeal(numeration,appealfl,appealdj,state,creatdt,enddt,receipt,groupnumber,ordernumber,contract,epro,cpro,number,unit,standard,transport,supplier,sdate,customer,cdate,appeallr,checknr,checkman,checkdate,clfa,clfaman,clfadate,feedback,feedbackman,feedbackdate,remarks,spmanger,spdate,spyj,cml,cfk,in_no) values('"+number+"','" + appealfl1 +"','" + appealdj1 + "','未办理','" + creatdt1 + "','" + enddt1 + "','" + receipt1 + "','" + groupnumber1 + "','" + ordernumber1 + "','" + contract1 + "','" + epro1 + "','" + cpro1 + "','" + number1 + "','" + unit1 + "','" + standard1 + "','" + transport1 + "','" + supplier1 + "','" + sdate1 + "','" + customer1 + "','" + cdate1 + "','" + appeallr1 + "','" + checknr1 + "','" + checkman1 + "','" + checkdate1 + "','" + clfa1 + "','" + clfaman1 + "','" + clfadate1 + "','" + feedback1 + "','" + feedbackman1 + "','" + feedbackdate1 + "','" + remarks1 + "','','','','80','',0)";
		 dbManager.executeUpdate(strSQL);
		 
		 return this.success();
	}
	
	public Map<String,Object> toHandle(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql="update appeal set state='办理中',clfadate='" + DateUtil.getCurrentDateSimpleStr() + "' where aid='" + id + "'";
		dbManager.update(sql);
		return this.success();
	}
	
}
