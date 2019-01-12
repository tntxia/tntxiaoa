package com.tntxia.oa.finance.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.finance.service.FinanceLightService;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceCreditViewHandler  extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	private FinanceLightService financeService = new FinanceLightService();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> detail = financeService.getCreditDebit(id);
		
		String sy = (String) detail.get("sy");
		String coname = (String) detail.get("l_coname");
		
		if("client".equals(sy)) {
			
			String clientMoney = null;
			
			String sqlc = "select  *  from payment_customer where coname  like '"+coname+"%'";
			
			List paymentCustomer = dbManager.queryForList(sqlc, true);
			
			BigDecimal qc = BigDecimal.ZERO;
			
			for(int i=0;i<paymentCustomer.size();i++) {
				Map<String,Object> map = (Map<String,Object>) paymentCustomer.get(i);
				clientMoney = (String) map.get("money");
				BigDecimal payment_je = (BigDecimal) map.get("payment_je");
				BigDecimal payment_sje = (BigDecimal) map.get("payment_sje");
				qc = payment_je.subtract(payment_sje);
			}
			
			detail.put("clientMoney", clientMoney);
			detail.put("clientQc", qc);
			
		}else if("supplier".equals(sy)) {
			String supplierMoney = null;
			
			String sqlc = "select  *  from gathering_customer where coname  like '"+coname+"%'";
			
			List paymentCustomer = dbManager.queryForList(sqlc, true);
			
			BigDecimal qc = BigDecimal.ZERO;
			
			for(int i=0;i<paymentCustomer.size();i++) {
				Map<String,Object> map = (Map<String,Object>) paymentCustomer.get(i);
				supplierMoney = (String) map.get("money");
				BigDecimal payment_je = (BigDecimal) map.get("payment_je");
				BigDecimal payment_sje = (BigDecimal) map.get("payment_sje");
				qc = payment_je.subtract(payment_sje);
			}
			
			detail.put("supplierMoney", supplierMoney);
			detail.put("supplierQc", qc);
		}
		
		this.putAllRootValue(detail);
		
		
	}
}
