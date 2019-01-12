package com.tntxia.oa.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.util.CommonAction;

/**
 * ������Action
 * 
 * @author tntxia
 *
 */
public class ManageItemAction extends CommonAction {
	
	private DBManager h2DBManager;
	
	public void setH2DBManager(DBManager h2dbManager) {
		h2DBManager = h2dbManager;
	}

	/**
	 * �г����еĺ�ͬģ��
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		return null;
		
	}
	
	/**
	 * �г����еĺ�ͬģ��
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("GB2312");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		
		String sql = "update manage_item set name = ?,url=? where id = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(name);
		params.add(url);
		params.add(id);
		
		h2DBManager.executeUpdate(sql, params);
		
		return super.exportCommon(true, new HashMap<String,Object>());
		
	}

}
