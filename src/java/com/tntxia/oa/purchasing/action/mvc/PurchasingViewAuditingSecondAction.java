package com.tntxia.oa.purchasing.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.warehouse.service.WarehouseLightService;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.DatasourceStore;

public class PurchasingViewAuditingSecondAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private WarehouseLightService service = new WarehouseLightService();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql = "select * from procure where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	private Map<String,Object> getSalePro(Integer id) throws Exception{
		String sql = "select * from ddpro where id = ?";
		return dbManager.queryForMap(sql,new Object[]{id}, true);
	}
	
	private Map<String,Object> getWarehouse(String epro) throws Exception{
		String sql = "select * from warehouse where pro_model = ?";
		return dbManager.queryForMap(sql, new Object[]{epro},true);
	}
	
	@SuppressWarnings({ "rawtypes" })
	private List getProList(String id) throws Exception{
		String sql = "select * from cgpro where ddid = ?";
		
		SQLExecutorSingleConn sqlExecutor = new SQLExecutorSingleConn(DatasourceStore.getDatasource("default"));
		
		List list = sqlExecutor.queryForList(sql,new Object[]{id}, true);
		
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			Integer sale_pro_id = (Integer) map.get("sale_pro_id");
			String epro = (String) map.get("epro");
			map.put("salePro", this.getSalePro(sale_pro_id));
			Map<String,Object> warehouse = this.getWarehouse(epro);
			if(warehouse!=null) {
				String pro_model = (String) warehouse.get("pro_model");
				int commingNum = service.getCommingNum(sqlExecutor, pro_model);
				int outingNum = service.getOutingNum(sqlExecutor, pro_model);
				warehouse.put("commingNum", commingNum);
				warehouse.put("outingNum", outingNum);
				map.put("warehouse", warehouse);
			}
			
		}
		
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private BigDecimal getTotalPrice(List proList){
		BigDecimal totalPrice = BigDecimal.ZERO;
		for(int i=0;i<proList.size();i++){
			
			Map map = (Map) proList.get(i);
			Integer num = (Integer) map.get("num");
			BigDecimal selljg = (BigDecimal) map.get("selljg");
			
			totalPrice = totalPrice.add(selljg.multiply(BigDecimal.valueOf(num)));
			
		}
		return totalPrice;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");
		this.putAllRootValue(this.getDetail(id));
		
		List proList = this.getProList(id);
		this.setRootValue("proList", proList);
		this.setRootValue("totalPrice", this.getTotalPrice(proList));
		
	}

}
