package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.system.dao.SystemDao;
import com.tntxia.oa.system.entity.SystemInfo;

public class UpdateSystemInfoAction implements Controller {
	
	private SystemDao systemDao;
	
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		request.setCharacterEncoding("GBK");
		
		SystemInfo info = systemDao.getSystemInfo();
		
		String companyName = request.getParameter("companyName");
		
		String website = request.getParameter("website");
		
		info.setCompanyName(companyName);
		info.setWebsite(website);
		
		systemDao.updateSystemInfo(info);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);

		return new ModelAndView("/common/result", result);
	}

}
