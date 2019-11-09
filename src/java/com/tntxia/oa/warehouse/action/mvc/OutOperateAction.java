package com.tntxia.oa.warehouse.action.mvc;

import java.util.List;
import java.util.Map;


import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.WarehouseManager;
import com.tntxia.web.mvc.WebRuntime;

public class OutOperateAction  extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	private SaleLightService saleService = new SaleLightService();

	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		Map<String,Object> detail = saleService.getDetail(id);
		this.putAllRootValue(detail);
		this.setRootValue("trview_yes", this.existRight(runtime, "trview_yes"));
		
		String coname = (String) detail.get("coname");
		String number = (String) detail.get("number");
		
		String sql = "select coaddr from client where coname='"+coname+"'";
		
		Map<String,Object> client = dbManager.queryForMap(sql, true);
		String coaddr = (String) client.get("coaddr");
		
		this.setRootValue("coaddr", coaddr);
		
		List<SalePro> productList = saleService.getProductList(id);
		
		for(SalePro salePro : productList){
			
			String epro = salePro.getEpro();
			
			WarehouseManager warehouseManager = new WarehouseManager();

			// 根据权限，看用户可以查看哪些仓库产品
			List<Warehouse> warehouseList = warehouseManager.getWarehouseByModelAndRestain(epro);
			salePro.setWarehouseList(warehouseList);
			
		}
		
		this.setRootValue("list", productList);
		
		String strSQLL="select * from outhouse where pro_fynum='"+number+"'";
		this.setRootValue("outList", dbManager.queryForList(strSQLL, true));
		
		
	}
	
}
