package com.tntxia.oa.mail.action;

import infocrmdb.DealString;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.util.CommonAction;

/**
 * 邮件Action
 * 
 * @author tntxia
 *
 */
public class MailAction extends CommonAction {
	
	private UserDao userDao;
	
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView newMail(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String mail_to = request.getParameter("mail_to");
		String mail_to2 = request.getParameter("mail_to2");
		String mail_to3 = request.getParameter("mail_to3");
		String mail_datetime = request.getParameter("mail_datetime");
		String mail_man = request.getParameter("mail_man");
		String dept = request.getParameter("w_dept");
		String deptjb = request.getParameter("deptjb");
		String mail_sub = request.getParameter("mail_sub");
		String mail_nr = request.getParameter("mail_nr");
		mail_nr = com.infoally.util.Replace.strReplace(mail_nr, "'", "''");
		String pizhu = request.getParameter("pizhu");

		mail_nr = DealString.htmlEncode(mail_nr);

		String[] pizhus = pizhu.split("<br>");
		int count = 0;
		for (int i = 0; i < pizhus.length; i++) {
			if (pizhus[i].length() > 0) {
				count++;
				
				String filename = pizhus[i].split("-")[1];
				mail_nr += "<br>附件"
						+ count
						+ "<br><img title=\"点击查看原图\" onclick=\"viewImg(this)\" width=200 height=200 src=\""
						+ request.getContextPath() + "/attachment_img/"
						+ filename + "\"/>";
			}
		}
		String strSQL = "insert into sendmail values('" + mail_to + "','"
				+ mail_to2 + "','" + mail_to3 + "','" + mail_sub + "','"
				+ mail_nr + "','" + mail_man + "','" + deptjb + "','" + dept
				+ "','" + mail_datetime + "','已发送','','','')";
		System.out.println(strSQL);
		boolean t = einfodb.executeUpdate(strSQL);
		if (!t) {

			resultMap.put("success", false);
			resultMap.put("message", "发送失败!");
			return new ModelAndView("common/result", resultMap);

		}

		resultMap.put("success", true);

		return new ModelAndView("common/result", resultMap);

	}

	/**
	 * 邮件发送
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getMail(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		String dept = (String) session.getAttribute("dept");
		String deptjb = (String) session.getAttribute("deptjb");
		String username1 = (String) session.getAttribute("username");
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd-HH-mm");
		String currentDate = simple.format(new java.util.Date());
		String strSQLproq = "select * from sendmail  where mail_to like '%"
				+ username1 + "%'  and  form_to  not  like '%" + username1
				+ "%'  or  mail_to2 like '%" + username1
				+ "%'  and  form_to2  not like '%" + username1
				+ "%'  or  mail_to3 like '%" + username1
				+ "%'  and  form_to3  not like '%" + username1 + "%'";
		ResultSet prspro = einfodb.executeQuery(strSQLproq);
		
		while (prspro.next()) {
			int id = prspro.getInt("id");
			String mail_to = prspro.getString("mail_to");
			String mail_to2 = prspro.getString("mail_to2");
			String mail_to3 = prspro.getString("mail_to3");
			String mail_sub = prspro.getString("mail_sub");
			String mail_nr = prspro.getString("mail_nr");
			String mail_man = prspro.getString("mail_man");
			String mail_datetime = prspro.getString("mail_datetime");
			String form_to = prspro.getString("form_to");
			String form_to2 = prspro.getString("form_to2");
			String form_to3 = prspro.getString("form_to3");
			String strSQL = "update sendmail  set form_to='" + form_to + ","
					+ username1 + "',form_to2='" + form_to2 + "," + username1
					+ "',form_to3='" + form_to3 + "," + username1
					+ "'  where id='" + id + "'";
			boolean t = einfodb.executeUpdate(strSQL);
			if (!t) {
				resultMap.put("success", false);
				resultMap.put("message", "收邮件失败!");
				return new ModelAndView("common/result", resultMap);

			}
			String strSQLn = "insert into  getmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,getman,form_datetime,states,sid) values('" + mail_to + "','"
					+ mail_to2 + "','" + mail_to3 + "','" + mail_sub + "','"
					+ mail_nr + "','" + mail_man + "','" + deptjb + "','"
					+ dept + "','" + mail_datetime + "','" + username1 + "','"
					+ currentDate + "','已收邮件','" + id + "')";
			boolean tn = einfodb.executeUpdate(strSQLn);
			if (!tn) {
				resultMap.put("success", false);
				resultMap.put("message", "收邮件失败!");
				return new ModelAndView("common/result", resultMap);
			}
			
		}

		resultMap.put("success", true);
		resultMap.put("jump", "/webmail/formmail.jsp");

		return new ModelAndView("common/result", resultMap);

	}
	
	@SuppressWarnings("rawtypes")
	public ModelAndView userSearch(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List userList = userDao.getList();
		
		resultMap.put("list", userList);
	    
	    return new ModelAndView("common/userSearch", resultMap);
		
	}
	
	public ModelAndView toSendMail(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		HttpSession session = request.getSession();

		String id_mail_in_session = request.getParameter("id_mail_in_session");

		String sendto = "";
		String content = "";
		String title = "";

		if(id_mail_in_session!=null){
			com.tntxia.oa.mail.entity.Mail mail = (com.tntxia.oa.mail.entity.Mail)session.getAttribute(id_mail_in_session);
			sendto = mail.getTo();
			content = mail.getContent();
			title = mail.getTitle();

		}else{
			sendto = request.getParameter("sendto");
			title = request.getParameter("title");
			for(String s : request.getParameterValues("content")){
				content += s+"\n\r\n";
			}
			
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sendto", sendto);
		resultMap.put("title", title);
		resultMap.put("content", content);
	    
	    return new ModelAndView("common/sendmail", resultMap);
		
	}

}
