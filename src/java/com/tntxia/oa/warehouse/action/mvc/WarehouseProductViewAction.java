package com.tntxia.oa.warehouse.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.warehouse.dao.WarehouseLightDao;
import com.tntxia.oa.warehouse.entity.PicInfo;
import com.tntxia.oa.warehouse.entity.SupplierPrice;
import com.tntxia.web.mvc.WebRuntime;


public class WarehouseProductViewAction  extends OACommonHandler{
	
	private WarehouseLightDao warehouseDao = new WarehouseLightDao();
	
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");

		Map<String,Object> warehouse = warehouseDao.getDetail(id);
		
		String model = (String) warehouse.get("pro_model");

		List<PicInfo> picList = warehouseDao.getPicIfo(id);
		
		this.setRootValue("warehouse", warehouse);
		this.setRootValue("picList", picList);	
		
		List<SupplierPrice> supplierPriceList = warehouseDao
				.getSupplierPriceList(model);

		this.setRootValue("supplierPriceList", supplierPriceList);
		
		this.setRootValue("warehouse_view_purchasing_price", this.existRight(runtime, "warehouse_view_purchasing_price"));
		
		
	}
	
}
