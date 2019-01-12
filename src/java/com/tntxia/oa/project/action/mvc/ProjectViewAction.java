package com.tntxia.oa.project.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class ProjectViewAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql = "select * from programer where id = ?";
		return dbManager.queryForMap(sql,new Object[]{id}, true);
	}

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		String id = runtime.getParam("id");
		Map<String,Object> detail = this.getDetail(id);
		if(detail!=null) {
			this.putAllRootValue(detail);
		}
		this.setRootValue("canMod",this.existRight(runtime, "xmmod"));
		this.setRootValue("canDel",this.existRight(runtime, "xmdel"));
	}

}
