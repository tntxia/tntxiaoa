package com.tntxia.oa.sale.handler;

import java.util.Map;

import com.tntxia.oa.sale.dao.SampleDao;

import com.tntxia.web.mvc.RedirectViewHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SampleDetailViewRedirectHandler extends RedirectViewHandler {
	
	private SampleDao sampleDao = new SampleDao();
	
	@Override
	public String init(WebRuntime runtime) throws Exception{
		
		String id=runtime.getParam("id");
		Map<String,Object> detail = sampleDao.getDetail(id);
		String state = (String) detail.get("state");
		if("未提交".equals(state)) {
			return "draft";
		}else if("待审批".equals(state)){
			return "ddd";
		}else if("样品已批准".equals(state)){
			return "f";
		}else if("已发运".equals(state)){
			return "toReturn";
		}
		return "default";
		
	}

}
