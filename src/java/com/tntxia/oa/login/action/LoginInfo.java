package com.tntxia.oa.login.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class LoginInfo extends CommonDoAction {
	
	/**
	 * 查看登陆信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> execute(WebRuntime runtime) throws Exception {
		
		String username = this.getUsername(runtime);
		
		Map<String,Object> logininfo = new HashMap<String,Object>();
		
		logininfo.put("username", username);
		
		logininfo.put("dept", this.getDept(runtime));
		
		logininfo.put("role", this.getRole(runtime));
		
		logininfo.put("loginTime", runtime.getSessionStr("currentDate"));
		
		List<String> userList =  (List<String>)runtime.getServletContext().getAttribute("userList");
		
		logininfo.put("loginList", userList);
		
		return logininfo;
		
		
		
	}

}
