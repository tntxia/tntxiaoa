package com.tntxia.oa.finance.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class FinancePaymentDetailAction  extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getPayment(String id) throws Exception{
		String sql="select  * from payview where id=?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getPurchasingProductList(String orderform) throws Exception{
		String sql = "select id,epro,num,cgpro_num,unit,money,selljg,cgpro_sdatetime from cgpro where ddid=?";
		return dbManager.queryForList(sql, new Object[]{orderform},true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		root.put("id", id);
		Map<String,Object> detail = this.getPayment(id);
		String orderform = (String) detail.get("orderform");
		
		List proList = this.getPurchasingProductList(orderform);
		
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
		
		root.put("detail", detail);
		
	}
}
