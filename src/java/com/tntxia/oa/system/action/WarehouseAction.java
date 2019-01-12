package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.WarehouseDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.util.Pager;

public class WarehouseAction extends CommonAction{
	
	private WarehouseDao warehouseSysDao;
	
	public void setWarehouseSysDao(WarehouseDao warehouseSysDao) {
		this.warehouseSysDao = warehouseSysDao;
	}

	/**
	 * 仓库管理列表
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return super.exportJSONObject(response, warehouseSysDao.list());
	}
	
	/**
	 * 删除仓库中所有的产品
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delWarehouseProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String warehouse = request.getParameter("warehouse");
		warehouseSysDao.delWarehouseProduct(warehouse);
		return super.getSuccessResult();
	}
	
	public ModelAndView delWarehouseProductNoUse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String warehouse = request.getParameter("warehouse");
		warehouseSysDao.deleteNoUseWarehouse(warehouse);
		return super.getSuccessResult();
	}
	
	public ModelAndView viewWarehouseProductNoUse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int pageNo = 1;
		int pageSize = Integer.MAX_VALUE;
		if (request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		if (request.getParameter("pageSize") != null) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		
		String warehouse = request.getParameter("warehouse");
		
		Pager pager = new Pager(pageNo, pageSize);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", pager.paging(warehouseSysDao.getWarehouseNoPic(warehouse)));
		result.put("pageNo", pageNo);
		result.put("pageSize", pageSize);
		result.put("totalPage", pager.getTotalPage());
		
		return super.getResult("system/warehouse/toDel", result);
	}
	
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 String number=request.getParameter("number");
		 String proflname1=request.getParameter("proflname");
		 String cpro1=request.getParameter("cpro");
		 String remark1=request.getParameter("remark");
		 
		 if(warehouseSysDao.exist(cpro1)){
			 return super.getErrorResult("仓库名称已存在！");
		 }
		 
		 Map<String,String> params = new HashMap<String,String>();
		 params.put("number", number);
		 params.put("place", proflname1);
		 params.put("name", cpro1);
		 params.put("remark", remark1);
		 
		warehouseSysDao.add(params);
		
		return super.getSuccessResult();
	}
	
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id1=request.getParameter("id");
		
		 String number=request.getParameter("number");
		 String proflname1=request.getParameter("proflname");
		 String cpro1=request.getParameter("cpro");
		 String remark1=request.getParameter("remark");
		 
		 Map<String,String> params = new HashMap<String,String>();
		 params.put("number", number);
		 params.put("place", proflname1);
		 params.put("name", cpro1);
		 params.put("remark", remark1);
		 params.put("id", id1);
		 
		warehouseSysDao.update(params);
		
		return super.getSuccessResult();
	}
	
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id1=request.getParameter("id");
		
		warehouseSysDao.delete(Integer.parseInt(id1));
		
		return super.getSuccessResult();
	}

}
