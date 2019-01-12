package com.tntxia.oa.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.RegionDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.web.ParamUtils;

public class RegionAction extends CommonAction {
	
	private RegionDao regionDao;
	
	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}
	
	public ModelAndView getProvinceList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, regionDao.getProvinceList());
	}

	public ModelAndView getProvinceById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.valueOf(request.getParameter("id"));
		return this.exportJSONObject(response, regionDao.getProvinceById(id));
	}
	
	public ModelAndView getCityListByProvinceId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.valueOf(request.getParameter("id"));
		return this.exportJSONObject(response, regionDao.getCityListByProvinceId(id));
	}
	
	public ModelAndView addCity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String city = ParamUtils.unescape(request, "name");
		int provinceId = ParamUtils.getInt(request, "provinceId");
		regionDao.addCity(city, provinceId);
		return this.exportSuccessJSON(response);
		
	}

}
