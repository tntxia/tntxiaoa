package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.PositionDao;
import com.tntxia.oa.system.entity.Position;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.util.Pager;

/**
 * 
 * 职位管理 控制器
 * 
 * @author tntxia
 *
 */
public class PositionAction extends CommonAction {

	private PositionDao positionDao;

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public ModelAndView list(HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		int pageNo = 1;
		int pageSize = Integer.MAX_VALUE;
		if (req.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(req.getParameter("pageNo"));
		}
		if (req.getParameter("pageSize") != null) {
			pageSize = Integer.parseInt(req.getParameter("pageSize"));
		}

		Pager pager = new Pager(pageNo, pageSize);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", pager.paging(positionDao.list()));
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("totalPage", pager.getTotalPage());

		return super.exportJSONObject(response, result);

	}

	public ModelAndView del(HttpServletRequest req, HttpServletResponse response)
			throws Exception {

		String id = req.getParameter("id");
		positionDao.del(Integer.parseInt(id));
		return super.getSuccessResult();

	}

	public ModelAndView toEdit(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		String id = req.getParameter("id");
		Position position = positionDao.getById(Integer.parseInt(id));
		return super.getResult("system/position/edit", position);
	}

	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String remark1 = request.getParameter("remark");
		
		Position pos = new Position();
		pos.setId(Integer.parseInt(id));
		pos.setRemark(remark1);
		positionDao.update(pos);
		return super.getSuccessResult();
	}
	
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String role_name=request.getParameter("role_name");
		String remark1=request.getParameter("remark");
		
		Position pos = new Position();
		
		pos.setName(role_name);
		pos.setRemark(remark1);
		positionDao.add(pos);
		return super.getSuccessResult();
	}

}
