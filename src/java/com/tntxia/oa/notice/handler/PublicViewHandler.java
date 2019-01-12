package com.tntxia.oa.notice.handler;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class PublicViewHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql="select  * from pub_table where id=?";
		return dbManager.queryForMap(sql,new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getDocList(String id) throws Exception{
		String sql = "select id,pic_sm,pic_path from p_pic where oid=?";
		return dbManager.queryForList(sql, new Object[]{id},true);
		
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		this.getRoot().putAll(this.getDetail(id));
		this.setRootValue("docList", this.getDocList(id));
	}

}
