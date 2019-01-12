package com.tntxia.oa.user.action;

import java.util.Map;

import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class UserLogoutAction extends BaseAction{
	
	public Map<String,Object> execute(WebRuntime runtime){
		runtime.invalideSession();
		return this.success();
	}

}
