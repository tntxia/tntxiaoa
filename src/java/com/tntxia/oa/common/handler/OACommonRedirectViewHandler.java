package com.tntxia.oa.common.handler;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tntxia.web.mvc.RedirectViewHandler;
import com.tntxia.web.mvc.WebRuntime;

public abstract class OACommonRedirectViewHandler extends RedirectViewHandler {

	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	protected String getUsername(WebRuntime runtime){
		
		return runtime.getSessionStr("username");
		
	}
	

	protected List<String> getUserRight(WebRuntime runtime){
		HttpSession session = runtime.getSession();
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		return userRightList;
	}
	
	/**
	 * 
	 * 判断是否存在权限
	 * 
	 */
	protected boolean existRight(WebRuntime runtime,String right){
		if(right==null){
			return true;
		}
		List<String> userRightList = getUserRight(runtime);
		return userRightList.contains(right);
	}
	

}
