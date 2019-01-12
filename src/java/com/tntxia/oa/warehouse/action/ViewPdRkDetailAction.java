package com.tntxia.oa.warehouse.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.warehouse.dao.WarehouseDao;
import com.tntxia.oa.warehouse.entity.WarehouseInProduct;

public class ViewPdRkDetailAction implements Controller {
	
	private WarehouseDao warehouseDao;
	
	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		request.setCharacterEncoding("GB2312");
		
		String model = request.getParameter("model");
		
		List<WarehouseInProduct> inList = warehouseDao.getInWarehouseListByModel(model);

		Map<String,Object> resultMap = new HashMap<String,Object>();

		resultMap.put("inList", inList);
		
		return new ModelAndView("warehouse/pd/inWarehouseDetail", resultMap);
	}

}
