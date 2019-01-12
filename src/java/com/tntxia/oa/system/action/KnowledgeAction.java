package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tntxia.oa.system.dao.KnowledgeDao;
import com.tntxia.oa.system.entity.KnowledgeType;

public class KnowledgeAction extends MultiActionController {

	private KnowledgeDao knowledgeDao;
	
	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}

	public ModelAndView typeList(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		request.setCharacterEncoding("GB2312");

		Map<String, Object> result = new HashMap<String, Object>();

		List<KnowledgeType> typeList = knowledgeDao.listTypes();
		
		result.put("result", typeList);

		return new ModelAndView("/system/knowledge/typeList", result);
	}
	
	public ModelAndView addType(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String name=request.getParameter("name");
		String remark=request.getParameter("remark");
		
		KnowledgeType type = new KnowledgeType();
		type.setName(name);
		type.setRemark(remark);
		
		
		knowledgeDao.addType(type);

		Map<String, Object> result = new HashMap<String, Object>();

		List<KnowledgeType> typeList = knowledgeDao.listTypes();
		
		result.put("result", typeList);

		return new ModelAndView("/system/knowledge/typeList", result);
	}
	
	public ModelAndView delType(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String id=request.getParameter("id");
		
		knowledgeDao.delTypeById(Integer.parseInt(id));

		Map<String, Object> result = new HashMap<String, Object>();

		List<KnowledgeType> typeList = knowledgeDao.listTypes();
		
		result.put("result", typeList);

		return new ModelAndView("/system/knowledge/typeList", result);
	}

	
}
