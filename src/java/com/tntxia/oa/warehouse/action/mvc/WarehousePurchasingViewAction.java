package com.tntxia.oa.warehouse.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.purchasing.dao.PurchasingProductDao;
import com.tntxia.web.mvc.WebRuntime;

public class WarehousePurchasingViewAction extends OACommonHandler {
	
	private PurchasingProductDao purchasingProductDao = new PurchasingProductDao();
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql = "select * from procure where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private BigDecimal getTotalPrice(List productList){
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		for(int i=0;i<productList.size();i++){
			Map map = (Map) productList.get(i);
			BigDecimal selljg = (BigDecimal) map.get("selljg");
			Integer num = (Integer) map.get("num");
			totalPrice = totalPrice.add(selljg.multiply(BigDecimal.valueOf(num)));
		}
		
		return totalPrice;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		Map<String,Object> detail = this.getDetail(id);
		String status = (String) detail.get("l_spqk");
		boolean showConfirm = false;
		if("ÉóÅúÍ¨¹ý".equals(status)){
			showConfirm = true;
		}
		root.put("id", id);
		root.put("detail", this.getDetail(id));
		root.put("showConfirm", showConfirm);
		
		List productList = purchasingProductDao.getProductByPurchasingId(id);
		root.put("productList", productList);
		root.put("productListSize", productList.size());
		root.put("totalPrice", this.getTotalPrice(productList));
		
		
		
	}

}
