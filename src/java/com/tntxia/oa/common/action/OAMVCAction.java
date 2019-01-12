package com.tntxia.oa.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tntxia.web.mvc.MVCAction;

public abstract class OAMVCAction extends MVCAction {

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
	
	protected String getRole(HttpServletRequest request){
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		return role;
	}

}
