package com.tntxia.oa.warehouse.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.currency.Rmb;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.company.service.CompanyService;
import com.tntxia.web.mvc.WebRuntime;

public class SendBillPrintHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	private CompanyService companyService = new CompanyService();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String sql = "select * from send_bill where id = ?";
		
		Map<String,Object> detail = dbManager.queryForMap(sql, new Object[] {id}, true);
		Integer companyId = (Integer) detail.get("company_id");
		sql = "select * from send_bill_pro where pid = ?";
		
		Map<String,Object> companyDetail = companyService.getDetail(companyId);
		this.setRootValue("company", companyDetail);
		
		List list = dbManager.queryForList(sql, new Object[] {id}, true);
		
		BigDecimal allTotal = BigDecimal.ZERO;
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer num = (Integer) map.get("num");
			BigDecimal price = (BigDecimal) map.get("price");
			BigDecimal total = BigDecimalUtils.multiply(num, price);
			allTotal = allTotal.add(total);
		}
		
		detail.put("list", list);
		
		detail.put("total", allTotal);
		detail.put("totalBig", Rmb.lowerToUpper(allTotal));
		this.putAllRootValue(detail);

	}

}
