package com.tntxia.oa.common.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tntxia.web.mvc.MVCPageHandler;
import com.tntxia.web.mvc.WebRuntime;

public abstract class OACommonHandler extends MVCPageHandler {
	
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
	
	/**
	 * 获取用户名
	 * @param request
	 * @return
	 */
	protected String getUsername(WebRuntime runtime){
		
		return runtime.getSessionStr("username");
		
	}
	
	/**
	 * 获取用户权限
	 * @param request
	 * @return
	 */
	protected String getRole(WebRuntime runtime){
		return runtime.getSessionStr("role");
	}
	
	/**
	 * 获取部门信息
	 * @param runtime
	 * @return
	 */
	protected String getDept(WebRuntime runtime){
		return runtime.getSessionStr("dept");
	}
	
	/**
	 * 获取部门编码
	 * @param runtime
	 * @return
	 */
	protected String getDeptjb(WebRuntime runtime){
		return runtime.getSessionStr("deptjb");
	}
	
	protected boolean canViewPrice(WebRuntime runtime){
		String role = this.getRole(runtime);
		return "总经理".equals(role) || "副总经理".equals(role) || "销售经理".equals(role);
	}

	@Override
	public Map<String, Object> execute(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		root.put("username", this.getUsername(runtime));
		root.put("dept", this.getDept(runtime));
		this.init(runtime);
		return root;
	}
	
	public abstract void init(WebRuntime runtime) throws Exception;
}
