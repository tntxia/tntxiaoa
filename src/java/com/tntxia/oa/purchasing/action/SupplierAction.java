package com.tntxia.oa.purchasing.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.purchasing.dao.SupplierDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;

public class SupplierAction extends CommonAction {

	private SupplierDao supplierDao;

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/**
	 * 获取供应商列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String coname = ParamUtils.unescape(request, "coname");
		Map<String,Object> param = new HashMap<String,Object>();
		PageBean pageBean = this.getPageBean(request, 20);
		param.put("pageBean", pageBean);
		param.put("coname", coname);
		param.put("country", ParamUtils.unescape(request, "country"));
		param.put("province", ParamUtils.unescape(request, "province"));
		param.put("tradetypes", ParamUtils.unescape(request, "tradetypes"));
		param.put("cojsfs", ParamUtils.unescape(request, "cojsfs"));
		param.put("scale", ParamUtils.unescape(request, "scale"));
		
		List list = supplierDao.list(param);
		List rows = this.getRows(list, pageBean);
		int totalAmount = supplierDao.getTotal(param);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", totalAmount);
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());

		return exportJSONObject(response, res);
	}

	public ModelAndView getPf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String number = request.getParameter("number");
		return exportJSONObject(response, supplierDao.getPf(number));
	}

}
