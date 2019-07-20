package com.tntxia.oa.purchasing.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingViewDraftAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	private Map getDetail(String id) throws Exception{
		String sql = "select * from procure where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	private String getSaleMan(String sub){
		if(StringUtils.isEmpty(sub)){
			return null;
		}
		String sql = "select man from subscribe where number=?";
		return dbManager.getString(sql, new Object[]{sub});
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");
		Map detail = this.getDetail(id);
		this.putAllRootValue(detail);
		
		String sub = (String) detail.get("sub");
		String subMan = this.getSaleMan(sub);
		setRootValue("subMan", subMan);
		// root.put("units", SystemCache.)
		List<String> rights = this.getUserRight(runtime);
		setRootValue("rights", rights);
		setRootValue("suppliers", SystemCache.suppliers);
	}

}
