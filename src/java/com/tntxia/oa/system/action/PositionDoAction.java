package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.dao.PositionDao;
import com.tntxia.oa.system.entity.Position;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.util.Pager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 职位管理 控制器
 * 
 * @author tntxia
 *
 */
public class PositionDoAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private List getPositionList() throws Exception{
		String sql = "select * from role";
		return dbManager.queryForList(sql, true);
	}
	
	private int getPositionCount() throws Exception{
		String sql = "select count(*) from role";
		return dbManager.getCount(sql);
	}

	public Map<String,Object> list(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);

		List list = getPositionList();
		
		return this.getPagingResult(list, pageBean, getPositionCount());

	}

}
