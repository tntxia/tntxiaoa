package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.CompanyDao;
import com.tntxia.oa.util.CommonAction;

/**
 * 合同模板Action
 * 
 * @author tntxia
 * 
 */
public class CompanyAction extends CommonAction {

	private CompanyDao companyDao;

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	/**
	 * 列出所有的合同模板
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView add(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		Map param = new HashMap();
		
		param.put("name", request.getParameter("companyname"));
		param.put("name2", request.getParameter("companyname2"));
		param.put("addr", request.getParameter("companyaddr"));
		param.put("addr2", request.getParameter("companyaddr2"));
		param.put("tel", request.getParameter("companytel"));
		param.put("fax", request.getParameter("companyfax"));

		param.put("bank", request.getParameter("companybank"));
		param.put("bank2", request.getParameter("companybank2"));
		
		param.put("bank_dm", request.getParameter("bank_dm"));
		param.put("bank_dm2", request.getParameter("bank_dm2"));
		param.put("number", request.getParameter("companynumber"));
		param.put("number2", request.getParameter("companynumber2"));
		
		param.put("bankroll", request.getParameter("bankroll"));
		param.put("com_bank_code", request.getParameter("com_bank_code"));
		param.put("com_bank_code2", request.getParameter("com_bank_code2"));
		param.put("com_sy_code", request.getParameter("com_sy_code"));
		param.put("com_sy_code2", request.getParameter("com_sy_code2"));
		
		param.put("com_sy_name", request.getParameter("com_sy_name"));
		param.put("com_sy_name2", request.getParameter("com_sy_name2"));
		param.put("companyman", request.getParameter("companyman"));
		param.put("companydm", request.getParameter("companydm"));
		param.put("companysh", request.getParameter("companysh"));
		
		param.put("com_sy_name", request.getParameter("com_sy_name"));
		param.put("com_sy_name2", request.getParameter("com_sy_name2"));
		param.put("companyman", request.getParameter("companyman"));
		param.put("companydm", request.getParameter("companydm"));
		param.put("companysh", request.getParameter("companysh"));
		
		param.put("companybm", request.getParameter("companybm"));
		param.put("companylxr", request.getParameter("companylxr"));
		param.put("companyemail", request.getParameter("companyemail"));
		param.put("companynet", request.getParameter("companynet"));
		param.put("remark", request.getParameter("remark"));

		companyDao.add(param);

		return super.getSuccessResult();

	}

}
