package com.tntxia.oa.system.action;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

/**
 * ºÏÍ¬Ä£°åAction
 * 
 * @author tntxia
 *
 */
public class ContactTemplateDoAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();

	public Map<String,Object> getTemplate(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		return dbManager.queryForMap("select  * from ht_mb where id=?", new Object[]{id}, true);
	}

}
