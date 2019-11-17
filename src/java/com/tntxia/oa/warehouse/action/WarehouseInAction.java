package com.tntxia.oa.warehouse.action;

import java.util.List;

import com.tntxia.oa.warehouse.dao.WarehouseLightDao;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseInAction extends BaseAction{
	
	private WarehouseLightDao warehouseDao = new WarehouseLightDao();
	
	/**
	 * 样品入库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List listSample(WebRuntime runtime) throws Exception {
		return warehouseDao.getSampleList();
	}

}
