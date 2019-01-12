package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.alibaba.fastjson.JSON;
import com.tntxia.oa.system.dao.RoleDao;
import com.tntxia.oa.system.entity.Role;

/**
 * 
 * 权限管理 控制器
 * 
 * @author tntxia
 *
 */
public class RoleAction implements Controller {
	
	private RoleDao roleDao;
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse arg1) throws Exception {
		
		List<Role> roleList = roleDao.getRoleList();
		
		String json = JSON.toJSONString(roleList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", json);
		
		return new ModelAndView("common/resultAjax", result);
	}

}
