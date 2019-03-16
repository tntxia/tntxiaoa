package com.tntxia.oa.mail.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 邮件Action
 * 
 * @author tntxia
 *
 */
public class MailDoAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();

	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> newMail(WebRuntime runtime) throws Exception {

		String mail_to = runtime.getParam("mail_to");
		String mail_to2 = runtime.getParam("mail_to2");
		String mail_to3 = runtime.getParam("mail_to3");
		String mail_datetime = DateUtil.getCurrentDateSimpleStr();
		String mail_man = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String deptjb = this.getDeptjb(runtime);
		String mail_sub = runtime.getParam("mail_sub");
		String mail_nr = runtime.getParam("mail_nr");

		String strSQL = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values(?,'"
				+ mail_to2 + "','" + mail_to3 + "','" + mail_sub + "',?,'" + mail_man + "','" + deptjb + "','" + dept
				+ "','" + mail_datetime + "','已发送','','','')";
		System.out.println(strSQL);
		dbManager.executeUpdate(strSQL,new Object[] {mail_to,mail_nr});
		return this.success();

	}
	
	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getMail(WebRuntime runtime) throws Exception {
		
		SQLExecutorSingleConn sqlExecutor=null;
		
		try{
			sqlExecutor = this.getSQLExecutorSingleConn();
			String dept = this.getDept(runtime);
			String deptjb = this.getDeptjb(runtime);
			String username = this.getUsername(runtime);
			String likeUsername = "%"+username+"%";
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String currentDate = simple.format(new java.util.Date());
			String strSQLproq = "select * from sendmail  "
					+ " where (mail_to like ? or  mail_to2 like ? or  mail_to3 like ?) and form_to  not like '%"+username+",%'";
			List list = sqlExecutor.queryForList(strSQLproq, new Object[] {likeUsername,likeUsername,likeUsername}, true);
			
			for(int i=0;i<list.size();i++) {
				Map map = (Map) list.get(i);
				Integer id = (Integer) map.get("id");
				String mail_to = (String)map.get("mail_to");
				String mail_to2 = (String)map.get("mail_to2");
				String mail_to3 = (String)map.get("mail_to3");
				String mail_sub = (String)map.get("mail_sub");
				String mail_nr = (String)map.get("mail_nr");
				String mail_man = (String)map.get("mail_man");
				String mail_datetime = (String)map.get("mail_datetime");
				String form_to = (String)map.get("form_to");
				if(StringUtils.isEmpty(form_to)) {
					form_to = username + ",";
				} else {
					form_to += username + ",";
				}
				
				String sql = "update sendmail set form_to = ? where id = ?";
				sqlExecutor.update(sql, new Object[] {form_to, id});
				
				String strSQLn = "insert into  getmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,getman,form_datetime,states,sid) values('" + mail_to + "','"
						+ mail_to2 + "','" + mail_to3 + "','" + mail_sub + "','"
						+ mail_nr + "','" + mail_man + "','" + deptjb + "','"
						+ dept + "','" + mail_datetime + "','" + username + "','"
						+ currentDate + "','已收邮件','" + id + "')";
				sqlExecutor.executeUpdate(strSQLn);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sqlExecutor!=null){
				sqlExecutor.close();
			}
		}

		
		return this.success();
	}
	
	/**
	 * 获取待收取邮件的数量
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getToGetCount(WebRuntime runtime) throws Exception {
		
		String username = this.getUsername(runtime);
		String likeUsername = "%"+username+"%";
		String strSQLn = "select count(*) from sendmail"
				+ " where (mail_to like ? or  mail_to2 like ? or  mail_to3 like ?) and form_to  not like '%"+username+",%'";
		
		int count = dbManager.getCount(strSQLn, new String[] {likeUsername,likeUsername,likeUsername});
		return this.success("count", count);
	}


}
