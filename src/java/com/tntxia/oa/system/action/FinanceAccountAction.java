package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tntxia.oa.system.dao.FinanceAccountDao;
import com.tntxia.oa.system.entity.FinanceAccount;
import com.tntxia.oa.system.entity.FinanceAccountDetail;

/**
 * 财务科目管理的Action类
 * @author tntxia
 *
 */
public class FinanceAccountAction  extends MultiActionController{
	
	private FinanceAccountDao financeAccountDao;
	
	public void setFinanceAccountDao(FinanceAccountDao financeAccountDao) {
		this.financeAccountDao = financeAccountDao;
	}



	/**
	 * 查看科目明细
	 * @return
	 */
	public ModelAndView viewAccountDetail(HttpServletRequest request,
			HttpServletResponse arg1){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		
		FinanceAccount account = financeAccountDao.getById(accountId);
		
		List<FinanceAccountDetail> financeAccountList = financeAccountDao.getFinanceDetailList(accountId);
		
		resultMap.put("account", account);
		resultMap.put("financeAccountList", financeAccountList);
		
		return new ModelAndView("system/account/accountDetail", resultMap);
		
	}
	
	/**
	 * 查看科目明细
	 * @return
	 */
	public ModelAndView toNewAccountDetail(HttpServletRequest request,
			HttpServletResponse arg1){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		
		FinanceAccount account = financeAccountDao.getById(accountId);
		
		resultMap.put("account", account);
		
		return new ModelAndView("system/account/newDetail", resultMap);
		
	}
	
	/**
	 * 查看科目明细
	 * @return
	 */
	public ModelAndView newAccountDetail(HttpServletRequest request,
			HttpServletResponse arg1){
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		int accountId = Integer.parseInt(request.getParameter("accountId"));
		
		String name = request.getParameter("km_mx");
		
		financeAccountDao.addDetail(accountId, name);
		
		resultMap.put("success", true);
		
		return new ModelAndView("common/result", resultMap);
		
	}
	
	

}
