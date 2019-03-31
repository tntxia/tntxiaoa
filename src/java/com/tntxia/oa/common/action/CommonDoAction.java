package com.tntxia.oa.common.action;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 用来显示页面的Action，
 * 不包含菜单
 * @author tntxia
 *
 */
public class CommonDoAction  extends BaseAction{
	
	public List<String> getUserRight(WebRuntime runtime){
		HttpSession session = runtime.getSession();
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		return userRightList;
	}
	
	protected boolean existRight(WebRuntime runtime,String right){
		if(right==null){
			return true;
		}
		List<String> userRightList = getUserRight(runtime);
		return userRightList.contains(right);
	}
	
	
	public List<String> getUserRight(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		return userRightList;
	}
	
	protected boolean existRight(HttpServletRequest request,String right){
		if(right==null){
			return true;
		}
		List<String> userRightList = getUserRight(request);
		return userRightList.contains(right);
	}
	
	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	protected String getUsername(HttpServletRequest request){
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		return username;
	}
	
	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	protected String getUsername(WebRuntime runtime){
		return runtime.getSessionStr("username");
	}
	
	protected Userinfo getUserinfo(HttpServletRequest request){
		
		Userinfo userinfo = new Userinfo();
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String dept = (String) session.getAttribute("dept");
		String deptjb = (String) session.getAttribute("deptjb");
		userinfo.setUsername(username);
		userinfo.setDept(dept);
		userinfo.setDeptjb(deptjb);
		
		return userinfo;
	
	}
	
	protected Userinfo getUserinfo(WebRuntime runtime){
		
		Userinfo userinfo = new Userinfo();
		
		String username = this.getUsername(runtime);
		String dept = this.getDept(runtime);
		String deptjb = this.getDeptjb(runtime);
		userinfo.setUsername(username);
		userinfo.setDept(dept);
		userinfo.setDeptjb(deptjb);
		userinfo.setRole(this.getRole(runtime));
		
		return userinfo;
	
	}
	
	/**
	 * 获取部门名
	 * @param request
	 * @return
	 */
	protected String getDept(HttpServletRequest request){
		HttpSession session = request.getSession();
		String dept = (String) session.getAttribute("dept");
		return dept;
	}
	
	/**
	 * 获取部门名
	 * @param request
	 * @return
	 */
	protected String getDept(WebRuntime runtime){
		return runtime.getSessionStr("dept");
		
	}
	
	/**
	 * 获取部门编号
	 * @param request
	 * @return
	 */
	protected String getDeptjb(HttpServletRequest request){
		HttpSession session = request.getSession();
		String dept = (String) session.getAttribute("deptjb");
		return dept;
	}
	
	/**
	 * 获取部门编号
	 * @param request
	 * @return
	 */
	protected String getDeptjb(WebRuntime runtime){
		
		return runtime.getSessionStr("deptjb");
		
	}
	
	/**
	 * 获取用户角色
	 * @param request
	 * @return
	 */
	protected String getRole(HttpServletRequest request){
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		return role;
	}
	
	/**
	 * 获取用户角色
	 * @param request
	 * @return
	 */
	protected String getRole(WebRuntime runtime){
		return runtime.getSessionStr("role");
		
	}
	
	protected boolean canViewPrice(WebRuntime runtime){
		String role = this.getRole(runtime);
		return "总经理".equals(role) || "副总经理".equals(role) || "销售经理".equals(role);
	}
	
	protected boolean isManager(WebRuntime runtime){
		String role = this.getRole(runtime);
		return "总经理".equals(role) || "副总经理".equals(role);
	}
	
	

}
