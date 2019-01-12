package com.tntxia.oa.system.action;

import infocrmdb.DealString;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.mail.dao.MailDao;
import com.tntxia.oa.mail.entity.Mail;
import com.tntxia.oa.system.dao.NoticeDao;

/**
 * 公告Action
 * 
 * @author tntxia
 *
 */
public class NoticeAction extends MultiActionController {

	private NoticeDao noticeDao;
	
	private MailDao mailDao;

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public void setMailDao(MailDao mailDao) {
		this.mailDao = mailDao;
	}



	/**
	 * 列出所有的公告
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		@SuppressWarnings("rawtypes")
		List noticeList = noticeDao.list();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", noticeList);

		return new ModelAndView("system/notice/list", result);

	}
	
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		int id = ServletRequestUtils.getIntParameter(request, "id", 0);
		noticeDao.delete(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);

		return new ModelAndView("common/result", result);
	}
	

	/**
	 * 增加公告审批流程
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addFlow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		Map<String, Object> result = new HashMap<String, Object>();

		String dd_man = request.getParameter("dd_man");
		String dept = request.getParameter("dept");
		String role = request.getParameter("role");
		String ifsp = request.getParameter("ifsp");
		String dd_date = request.getParameter("dd_date");
		String remark1 = request.getParameter("remark");
		String strSQL = "insert into htsp(dept,role,ifsp,dd_man,remark,dd_date) values('" + dept + "','" + role
				+ "','" + ifsp + "','" + dd_man + "','" + remark1 + "','"
				+ dd_date + "')";
		boolean t = db.executeUpdate(strSQL);
		if (!t) {
			result.put("success", false);
			result.put("msg", "<font size='2' color='#FF0000'>添加失败,你所输入的内容超出系统范围或输入类型不符!</font>");
			return new ModelAndView("common/result",result);
		}
		
		result.put("success", true);
		return new ModelAndView("common/result",result);
	}
	
	/**
	 * 通过公告审批
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView approve(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		DBConnection einfodb = new DBConnection();
		
		String id=request.getParameter("id");
		String states=request.getParameter("states");
		String content1=request.getParameter("content");
		content1 = DealString.htmlEncode(content1);
		content1=com.infoally.util.Replace.strReplace(content1,"'","''");
		String strSQL="update pub_table set  states='"+states+"',content='"+content1+"'   where   id='"+id+"'";

		boolean t= einfodb.executeUpdate(strSQL);
		   
		if(!t){
			result.put("success", false);
			result.put("message", "失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result",result);
		    
		}

		String sql = "select titel,content,dept,username from pub_table where id = '"+id+"'";

		ResultSet rs = einfodb.executeQuery(sql);

		String mailTitle = "";    // 邮件标题
		String mailContent = "";  // 邮件内容
		String dept = "";         // 部门
		String username = "";     // 提交审批的人

		if(rs.next()){
			mailTitle = rs.getString(1);
			mailContent = rs.getString(2);
			dept = rs.getString(3);
			username = rs.getString("username");
			rs.close();
		}else{
			result.put("success", false);
			result.put("message", "没有该公告的信息");
			rs.close();
			return new ModelAndView("common/result",result);
			
		}

		sql = "select name from username";

		if(dept!=null && !dept.equals("全体员工")){
			sql += " where yjxs ='"+dept+"'";
		}


		rs = einfodb.executeQuery(sql);

		while(rs.next()){
			
			String name = rs.getString("name");
			
			if(name!=null && !name.equals("")){
				Mail mail = new Mail();
				mail.setTitle(mailTitle);
				mail.setContent(mailContent);
				mail.setFrom(username);
				mail.setTo(name);
				mailDao.sendMail(mail);
			}
		}


		result.put("success", true);
		return new ModelAndView("common/result",result);
	}

}
