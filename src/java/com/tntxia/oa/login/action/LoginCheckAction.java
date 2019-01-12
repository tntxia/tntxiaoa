package com.tntxia.oa.login.action;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.oa.common.action.CommonDoAction;

public class LoginCheckAction extends CommonDoAction {
	
	public Map<String,Object> execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return this.success("login",
				request.getSession().getAttribute("username")!=null);
	}

}
