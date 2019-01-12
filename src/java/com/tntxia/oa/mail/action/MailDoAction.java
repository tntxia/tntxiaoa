package com.tntxia.oa.mail.action;

import java.util.Map;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;

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


}
