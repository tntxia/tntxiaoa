package com.tntxia.oa.mvc.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.system.dao.SystemDao;
import com.tntxia.oa.system.entity.SystemInfo;

public class ViewSystemInfoAction implements Controller {
	
	private SystemDao systemDao;
	
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		SystemInfo info = systemDao.getSystemInfo();
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("companyName", info.getCompanyName());
		result.put("website", info.getWebsite());

		return new ModelAndView("sinfo", result);
	}
}
