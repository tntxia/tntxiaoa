package com.tntxia.oa.common.handler;

import java.util.List;
import java.util.Map;

import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.RequestUtils;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 处理类 带OA公共头部和左边菜单处理器
 * 
 * @author tntxia
 *
 */
public abstract class HandlerWithHeaderAndLeftbar extends HandlerWithHeader{
	
	private WebRuntime runtime;
	
	public WebRuntime getRuntime() {
		return runtime;
	}
	
	@SuppressWarnings("rawtypes")
	public void setLeftbar(String type,int firstSelect,int secondSelect){
		Map<String,Object> root = this.getRoot();
		List leftbar = SystemCache.leftbarMap.get(type);
		root.put("leftbar", leftbar);
		root.put("leftbarFirstSelected", firstSelect);
		root.put("leftbarSecondSelected", secondSelect);
	}
	
	public Map<String,Object> execute(WebRuntime runtime) throws Exception{
		this.runtime = runtime;
		
		
		Integer restrainId = RequestUtils.getRestrainId(runtime);
		this.setRootValue("username", this.getUsername(runtime));
		this.setRootValue("dept", this.getDept(runtime));
		this.setRootValue("role", this.getRole(runtime));
		this.getRoot().put("menus", this.getMenus(restrainId));
		init(runtime);
		return this.getRoot();
	}
	

}
