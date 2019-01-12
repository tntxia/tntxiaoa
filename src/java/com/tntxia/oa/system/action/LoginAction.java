package com.tntxia.oa.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.tntxia.oa.system.dao.SystemDao;
import com.tntxia.oa.util.WebUtils;

public class LoginAction implements Controller {

	private SystemDao systemDao;

	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		
		boolean existFlag = systemDao.existAdmin(username, password);
		
		if(existFlag){
			WebUtils.exportSuccessJson(response);
			request.getSession().setAttribute("admin", username);
		}else{
			WebUtils.exportErrorJSON(response, "µÇÂ½Ê§°Ü");
		}

		return null;
	}

}
