package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.system.dao.RightDao;

/**
 * 
 * 权限管理 控制器
 * 
 * @author tntxia
 *
 */
public class RoleRightUpdateAction implements Controller {
	
	private RightDao rightDao;
	
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse arg1) throws Exception {
		
		String roleId = req.getParameter("roleId");
		
		String rightStr =  req.getParameter("rightStr");
		
		rightDao.updateRight(Integer.parseInt(roleId), rightStr);
		
		JSONObject json = new JSONObject();
		json.put("success", true);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", json.toJSONString());
		
		return new ModelAndView("common/resultAjax", result);
	}

}
