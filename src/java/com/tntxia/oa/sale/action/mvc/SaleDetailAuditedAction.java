package com.tntxia.oa.sale.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.warehouse.service.WarehouseLightService;
import com.tntxia.web.mvc.WebRuntime;

public class SaleDetailAuditedAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private SaleLightService service = new SaleLightService();
	
	private WarehouseLightService warehouseService = new WarehouseLightService();
	
	@SuppressWarnings("rawtypes")
	private List getUnitList() throws Exception{
		String sql = "select * from punit_type";
		return dbManager.queryForList(sql,true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAuditFlows(String dept,String role) throws Exception{
		String sql = "select  * from ddsp  where  dept=? and role=?";
		return dbManager.queryForList(sql,new Object[]{dept,role}, true	);
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		Map<String,Object> root = this.getRoot();
		Map<String,Object> detail = service.getDetail(id);
		
		String state = (String) detail.get("state");
		if(state!=null){
			state = state.trim();
		}
		
		if("待出库".equals(state)){
			detail.put("isToOut", true);
		}else{
			detail.put("isToOut", false);
		}
		
		root.put("detail", detail);
		
		String dept = this.getDept(runtime);
		String role = this.getRole(runtime);
		
		String item = (String)detail.get("item");
		
		double tax=0;
		
		if(item.equals("增值发票17%")){
			tax=0.17;
		}else if(item.equals("普通发票6%")){
			tax=0.06;
		}else if(item.equals("普通发票4%")){
			tax=0.04;
		}
		
		List<SalePro> list = service.getProductList(id);
		BigDecimal zprice=BigDecimal.ZERO;
		for(SalePro pro : list){
			zprice=zprice.add(pro.getTotalPrice());
			
			BigDecimal selljg = warehouseService.getWarehousePrice(pro.getEpro());
			pro.setSelljg(selljg);
			
			pro.calcProfit(tax);
		}
		
		root.put("proList",list);
		root.put("unitList", this.getUnitList());
		root.put("zprice", zprice);
		root.put("opdel", super.existRight(runtime, "opdel"));
		root.put("subview_yes", super.existRight(runtime, "subview_yes"));
		root.put("proListSize", list.size());
		root.put("canPrint", "总经办".equals(dept));
		root.put("auditFlows", this.getAuditFlows(dept, role));
		
		root.put("suppliers", SystemCache.suppliers);
		
	}

}
