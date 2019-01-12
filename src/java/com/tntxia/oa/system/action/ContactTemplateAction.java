package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.ContactTemplateDao;
import com.tntxia.oa.system.entity.ContactTemplate;
import com.tntxia.oa.util.CommonAction;

/**
 * 合同模板Action
 * 
 * @author tntxia
 *
 */
public class ContactTemplateAction extends CommonAction {

	private ContactTemplateDao contactTemplateDao;
	
	public void setContactTemplateDao(ContactTemplateDao contactTemplateDao) {
		this.contactTemplateDao = contactTemplateDao;
	}

	/**
	 * 列出所有的合同模板
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		List<ContactTemplate> currentTypeList = contactTemplateDao.list();
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("list", currentTypeList);
		
		return new ModelAndView("system/contact_template/list", result);
		
	}
	
	
	
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		String q_topic=request.getParameter("q_topic");
		 String q_company=request.getParameter("q_company");
		 String q_addr=request.getParameter("q_addr");
		 String q_tel=request.getParameter("q_tel");
		 String q_fax=request.getParameter("q_fax");
		 String q_email=request.getParameter("q_email");
		 String q_net=request.getParameter("q_net");
		 String q_tk=request.getParameter("q_tk");
		 
		 String q_man=request.getParameter("q_man");
		 String remark=request.getParameter("remark");
		 String q_date=request.getParameter("q_date");
		 String q_post=request.getParameter("q_post");
		 String q_number=request.getParameter("q_number");
		 String q_name=request.getParameter("q_name");
		 String dept = request.getParameter("dept");
		 
		 params.put("q_topic", q_topic);
		 params.put("q_company", q_company);
		 params.put("q_addr", q_addr);
		 params.put("q_tel", q_tel);
		 params.put("q_fax", q_fax);
		 params.put("q_email", q_email);
		 params.put("q_net", q_net);
		 params.put("q_tk", q_tk);
		 params.put("q_man", q_man);
		 params.put("remark", remark);
		 params.put("q_date", q_date);
		 params.put("q_post", q_post);
		 params.put("q_number", q_number);
		 params.put("q_name", q_name);
		 params.put("dept", dept);
		
		contactTemplateDao.add(params);
		
		return super.getSuccessResult();
	}
	
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		String id=request.getParameter("id");
		String q_topic=request.getParameter("q_topic");
		 String q_company=request.getParameter("q_company");
		 String q_addr=request.getParameter("q_addr");
		 String q_tel=request.getParameter("q_tel");
		 String q_fax=request.getParameter("q_fax");
		 String q_email=request.getParameter("q_email");
		 String q_net=request.getParameter("q_net");
		 String q_tk=request.getParameter("q_tk");
		 
		 String q_man=request.getParameter("q_man");
		 String remark=request.getParameter("remark");
		 String q_date=request.getParameter("q_date");
		 String q_post=request.getParameter("q_post");
		 String q_number=request.getParameter("q_number");
		 String q_name=request.getParameter("q_name");
		 String dept = request.getParameter("dept");
		 
		 params.put("id", id);
		 params.put("q_topic", q_topic);
		 params.put("q_company", q_company);
		 params.put("q_addr", q_addr);
		 params.put("q_tel", q_tel);
		 params.put("q_fax", q_fax);
		 params.put("q_email", q_email);
		 params.put("q_net", q_net);
		 params.put("q_tk", q_tk);
		 params.put("q_man", q_man);
		 params.put("remark", remark);
		 params.put("q_date", q_date);
		 params.put("q_post", q_post);
		 params.put("q_number", q_number);
		 params.put("q_name", q_name);
		 params.put("dept", dept);
		
		contactTemplateDao.update(params);
		
		return super.getSuccessResult();
	}
	
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String id=request.getParameter("id");
	
		contactTemplateDao.del(Integer.parseInt(id));
		
		return super.getSuccessResult();
	}

}
