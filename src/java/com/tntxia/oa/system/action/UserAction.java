package com.tntxia.oa.system.action;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.dao.DepartmentDao;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.system.entity.User;

/**
 * 用户Action
 * 
 * @author tntxia
 *
 */
public class UserAction extends MultiActionController {

	private DepartmentDao departmentDao;

	private UserDao userDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 列出所有销售部门的用户
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView listSale(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String departmentId = request.getParameter("departmentId");
		
		String departmentName = null;
		
		if(departmentId!=null){
			Department dept = departmentDao.getDepartment(Integer.parseInt(departmentId));
			departmentName = dept.getName();
		}
		
		List<User> userList = userDao.getSaleUserList(departmentName);

		String json = JSON.toJSONString(userList);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", json);

		return new ModelAndView("common/resultAjax", result);

	}

	public ModelAndView register(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		String name = request.getParameter("name");
		String nameEn = request.getParameter("nameEn");
		String sex = request.getParameter("sex");
		String password = request.getParameter("password");
		String department1 = request.getParameter("department");
		String workj1 = request.getParameter("workj");
		String email1 = request.getParameter("email");
		String tel = request.getParameter("worktel");
		String restrainId = request.getParameter("restrainId");
		String hometel1 = request.getParameter("hometel");
		String user_ip = request.getParameter("user_ip");
		String ipbd = request.getParameter("ipbd");
		String mail_user = request.getParameter("mail_user");
		String mail_password = request.getParameter("mail_password");
		String mail_smtp = request.getParameter("mail_smtp");

		User user = new User();
		user.setName(name);
		user.setNameEn(nameEn);
		user.setSex(sex);
		user.setPassword(password);
		
		user.setDepartmentId(Integer.parseInt(department1));
		user.setPosition(workj1);
		user.setTel(tel);
		user.setRestrainId(Integer.parseInt(restrainId));
		user.setEmail(email1);
		user.setTelHome(hometel1);
		user.setIp(user_ip);
		user.setIpBind(Boolean.parseBoolean(ipbd));
		user.setEmailUser(mail_user);
		user.setEmailPassword(mail_password);
		user.setEmailStmp(mail_smtp);

		Department depart = departmentDao.getDepartment(Integer
				.parseInt(department1));

		if (depart == null) {
			result.put("success", false);
			result.put("message", "所填写的部门信息不存在");
			return new ModelAndView("/common/result", result);
		}

		userDao.addUser(user);

		result.put("success", true);
		result.put("openerReload", true);

		return new ModelAndView("/common/result", result);
	}

	public ModelAndView toChangePass(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		HttpSession session = request.getSession();

		String name = (String) session.getAttribute("username");

		User user = userDao.getUser(name);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", user);
		return new ModelAndView("/setting/passchange", result);

	}

	

	/**
	 * 跳转到用户修改的页面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 */
	public ModelAndView toChangeUser(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		int id = ServletRequestUtils.getIntParameter(request, "id");

		User user = userDao.getUserById(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", user);
		return new ModelAndView("/system/user/change", result);

	}

	/**
	 * 跳转到用户修改的页面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		int id = ServletRequestUtils.getIntParameter(request, "id");
		String name1 = request.getParameter("name").trim();
		String sex1 = request.getParameter("sex");
		String password = request.getParameter("password");
		String nameEn = request.getParameter("nameEn");
		String email1 = request.getParameter("email");
		String position = request.getParameter("position");
		String restrainId = request.getParameter("restrainId");
		String waptel1 = request.getParameter("waptel");
		String worktel = request.getParameter("worktel");
		String ipbd = request.getParameter("ipbind");
		
		int deptId = ServletRequestUtils.getIntParameter(request, "deptId");
		Department department = departmentDao.getDepartment(deptId);
		String deptName = department.getName();

		String mail_user = request.getParameter("mail_user");
		String mail_password = request.getParameter("mail_password");
		String mail_smtp = request.getParameter("mail_smtp");
		String sql = "select  * from department where id=" + deptId;
		
		DBConnection db = new DBConnection();
		
		String errorMsg = "";
		
		ResultSet rs = db.executeQuery(sql);
		if (!rs.next()) {
			errorMsg = "部门ID不存在";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
		}
		String deptjb = rs.getString("dept_code");

		String strSQL = "update username set   mail_user='" + mail_user
				+ "',mail_password='" + mail_password + "',mail_smtp='"
				+ mail_smtp + "',sex='" + sex1 + "',department_id = " + deptId
				+ ",yjxs='" + deptName + "',hometel='" + deptjb
				+ "',password='" + password + "',name_en='" + nameEn
				+ "',workj='" + position + "',email='" + email1 + "',worktel='"
				+ worktel + "',restrain_id=" + restrainId + ",user_ip='"
				+ waptel1 + "',ipbd='" + ipbd + "' where nameid='" + id + "'";
		boolean tq1q1 = db.executeUpdate(strSQL);
		if (!tq1q1) {
			errorMsg = "<font size='2' color='#FF0000'>更新失败!</font>" + strSQL;
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}

		String strSQLman = "update linkman   set  dept='" + deptName
				+ "',deptjb='" + deptjb + "'  where   username='" + name1
				+ "' ";
		boolean tman = db.executeUpdate(strSQLman);
		if (!tman) {
			errorMsg = "<font size='2' color='#FF0000'>转移联系人失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLop = "update opportunity set   dept='" + deptName
				+ "',deptjb='" + deptjb + "'  where   username='" + name1 + "'";
		boolean top = db.executeUpdate(strSQLop);
		if (!top) {
			
			errorMsg = "<font size='2' color='#FF0000'>转移销售机会失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
		}
		String strSQLact = "update activity set state='" + deptName
				+ "',deptjb='" + deptjb + "'  where  name='" + name1 + "' ";
		boolean tact = db.executeUpdate(strSQLact);
		if (!tact) {
			errorMsg = "<font size='2' color='#FF0000'>转移客户跟进失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLq = "update quote set dept='" + deptName + "',deptjb='"
				+ deptjb + "'  where   man='" + name1 + "' ";
		boolean tq = db.executeUpdate(strSQLq);
		if (!tq) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户报价失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}

		String strSQLsub1 = "update subscribe set dept='" + deptName
				+ "',deptjb='" + deptjb + "'  where   man='" + name1 + "' ";
		boolean tsub1 = db.executeUpdate(strSQLsub1);
		if (!tsub1) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户出库失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}

		String strSQLopin = "update Inquiry set dept='" + deptName
				+ "',deptjb='" + deptjb + "'  where   man='" + name1 + "' ";
		boolean topin = db.executeUpdate(strSQLopin);
		if (!topin) {
			errorMsg ="<font size='2' color='#FF0000'>转移销售机会失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLc = "update client set dept='" + deptName + "',deptjb='"
				+ deptjb + "' where  username='" + name1 + "'";
		boolean tc = db.executeUpdate(strSQLc);
		if (!tc) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户资料失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLfw = "update work_report set w_dept='" + deptName
				+ "',w_deptjb='" + deptjb + "'  where    w_man='" + name1
				+ "' ";
		boolean tworkw = db.executeUpdate(strSQLfw);
		if (!tworkw) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户收款信息失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLfw1 = "update work_report_rd set w_dept='" + deptName
				+ "',w_deptjb='" + deptjb + "'  where    w_man='" + name1
				+ "' ";
		boolean tworkw1 = db.executeUpdate(strSQLfw1);
		if (!tworkw1) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户收款信息失败!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLm = "update menber set  dept='" + deptName + "',deptjb='"
				+ deptjb + "' where   name='" + name1 + "' ";
		boolean tm = db.executeUpdate(strSQLm);
		if (!tm) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户资料失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLmz = "update supplier set  dept='" + deptName
				+ "',deptjb='" + deptjb + "' where    username='" + name1
				+ "' ";
		boolean tmz = db.executeUpdate(strSQLmz);
		if (!tmz) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户资料失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLmzl = "update qlinkman set  dept='" + deptName
				+ "',deptjb='" + deptjb + "' where    username='" + name1
				+ "' ";
		boolean tmzl = db.executeUpdate(strSQLmzl);
		if (!tmzl) {
			errorMsg ="<font size='2' color='#FF0000'>转移客户资料失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLmzl1 = "update sample set  dept='" + deptName
				+ "',deptjb='" + deptjb + "' where   man='" + name1 + "' ";
		boolean tmzl1 = db.executeUpdate(strSQLmzl1);
		if (!tmzl1) {
			errorMsg ="<font size='2' color='#FF0000'>转移失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLmzl2 = "update programer set  dept='" + deptName
				+ "',deptjb='" + deptjb + "' where   application='" + name1
				+ "' ";
		boolean tmzl2 = db.executeUpdate(strSQLmzl2);
		if (!tmzl2) {
			errorMsg ="<font size='2' color='#FF0000'>转移失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
			
		}
		String strSQLmzl3 = "update th_table set  dept='" + deptName
				+ "',deptjb='" + deptjb + "' where   man='" + name1 + "' ";
		boolean tmzl3 = db.executeUpdate(strSQLmzl3);
		if (!tmzl3) {
			errorMsg ="<font size='2' color='#FF0000'>转移失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
		}

		String strSQLmzl33 = "update credit_debit set  l_dept='" + deptName
				+ "',l_deptjb='" + deptjb + "' where   l_man='" + name1 + "' ";
		boolean tmzl33 = db.executeUpdate(strSQLmzl33);
		if (!tmzl33) {
			errorMsg ="<font size='2' color='#FF0000'>转移失败,!</font>";
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", errorMsg);
			return new ModelAndView("/common/result", result);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return new ModelAndView("/common/result", result);

	}
	
	/**
	 * 跳转到用户修改的页面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		int id = ServletRequestUtils.getIntParameter(request, "id");
		userDao.deleteUser(id);
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Boolean> resultMsg = new HashMap<String,Boolean>();
		resultMsg.put("success", true);
		String json = JSON.toJSONString(resultMsg);
		result.put("result", json);
		return new ModelAndView("/common/resultAjax", result);
	}
	
	

}
