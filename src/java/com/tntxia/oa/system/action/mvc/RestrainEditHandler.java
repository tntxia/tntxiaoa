package com.tntxia.oa.system.action.mvc;

import java.util.HashMap;
import java.util.Map;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.dao.RightLightDao;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 权限组列表
 * @param request
 * @param response
 * @return
 */
public class RestrainEditHandler extends OACommonHandler {
	
	private RightLightDao rightDao  = new RightLightDao();
	

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id1 = runtime.getParam("id");
		
		Map<String,Object> restrainMap = rightDao.getRestrainMap(id1);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultMap", restrainMap);
		this.putAllRootValue(restrainMap);

	}

}
