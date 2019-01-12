package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.tntxia.oa.system.dao.CurrentTypeDao;
import com.tntxia.oa.system.entity.CurrentType;

/**
 * ��������Action
 * 
 * @author tntxia
 *
 */
public class CurrentTypeAction extends MultiActionController {

	private CurrentTypeDao currentTypeDao;
	
	public void setCurrentTypeDao(CurrentTypeDao currentTypeDao) {
		this.currentTypeDao = currentTypeDao;
	}
	
	/**
	 * �г����еĻ������ͣ���JSON��ʽ����
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		List<CurrentType> currentTypeList = currentTypeDao.getCurrentTypeList();
		
		String json = JSON.toJSONString(currentTypeList);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", json);
		
		return new ModelAndView("common/resultAjax", result);
		
	}

}
