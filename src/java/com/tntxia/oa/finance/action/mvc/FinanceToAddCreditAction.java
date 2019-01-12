package com.tntxia.oa.finance.action.mvc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.finance.service.FinanceLightService;
import com.tntxia.oa.purchasing.service.PurchasingLightService;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceToAddCreditAction  extends OACommonHandler{


	private DBManager dbManager = this.getDBManager();
	
	private FinanceLightService financeService = new FinanceLightService();
	
	private PurchasingLightService purchasingService = new PurchasingLightService();
	
	/**
	 * 获取科目名称的列表
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private List getAccountTypeList() throws Exception{
		String sql = "select * from km_mx";
		return dbManager.queryForList(sql, true);
	}


	@SuppressWarnings({ "rawtypes" })
	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		root.put("id", id);
		Map<String,Object> detail = financeService.getPayment(id);
		String orderform = (String) detail.get("orderform");
		
		List proList = purchasingService.getPurchasingProductList(this.getDBManager(),orderform);
		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal stotal = BigDecimal.ZERO;
		
		for(int i=0;i<proList.size();i++){
			Map map = (Map) proList.get(i);
			Integer num = (Integer) map.get("num");
			Integer rknum = (Integer) map.get("cgpro_num");
			BigDecimal selljg = (BigDecimal) map.get("selljg");
			BigDecimal proPriceTotal = selljg.multiply(new BigDecimal(num));
			total = total.add(selljg.multiply(new BigDecimal(rknum)));
			stotal = stotal.add(proPriceTotal);
			map.put("proPriceTotal", proPriceTotal);
		}
		
		// 采购金额和入库金额
		root.put("total", total);
		root.put("stotal", stotal);
		
		root.put("proList", proList);
		root.put("accountTypeList", this.getAccountTypeList());
		root.put("deptList", SystemCache.departmentList);
		root.put("currentTypeList", SystemCache.currentList);
		
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		String currentDate=simple.format(new java.util.Date());
		
		root.put("detail", detail);
		root.put("currentDate", currentDate);
		
	}
}
