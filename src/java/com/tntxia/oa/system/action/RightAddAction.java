package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.system.dao.RightDao;
import com.tntxia.oa.system.entity.Right;

/**
 * 
 * 权限管理 控制器
 * 
 * @author tntxia
 *
 */
public class RightAddAction implements Controller {
	
	private RightDao rightDao;
	
	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse arg1) throws Exception {
		
		req.setCharacterEncoding("gb2312");
		String name = req.getParameter("name");
		String display = req.getParameter("display");
		Right param = new Right();
		param.setRightName(name);
		param.setRightDisplay(display);
		rightDao.addRight(param);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		
		return new ModelAndView("common/result", result);
	}

}
