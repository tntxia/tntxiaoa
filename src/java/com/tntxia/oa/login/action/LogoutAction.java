package com.tntxia.oa.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.util.PropertiesUtils;


public class LogoutAction implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.getSession().invalidate();

		String pre = PropertiesUtils.getProperty("oa.pre");
		String loginPath = "/template/"+pre+"login.jsp";
		request.getRequestDispatcher(loginPath).forward(request, response);
		return null;
	}

}
