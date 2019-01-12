package com.tntxia.oa.warehouse.action.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.view.FTLView;

public class CheckInViewAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("default");
	
	@SuppressWarnings("rawtypes")
	private Map getHdCompany(String id) throws Exception{
		String sql="select  * from hdcompany where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAttachList(String id) throws Exception{
		String sql = "select id,filename,filepath,remark from hdcompany_attachment where hdcompanyid = ?";
		return dbManager.queryForList(sql, new Object[]{id}, true);
	}
	
	public FTLView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("comp", getHdCompany(id));
		res.put("attachments", getAttachList(id));
		
		return new FTLView("dzzz/ysgl/hd-view.ftl",res);
		
	}
	
}
