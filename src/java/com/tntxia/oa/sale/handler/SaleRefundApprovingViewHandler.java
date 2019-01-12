package com.tntxia.oa.sale.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SaleRefundApprovingViewHandler extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from th_table where id = ?";
		Map<String,Object> detail = dbManager.queryForMap(sql, new Object[] {id}, true);
		this.putAllRootValue(detail);
		
		List list = dbManager.queryForList("select * from th_pro where ddid = ?", new Object[] {id}, true);
		BigDecimal totalAll = BigDecimal.ZERO;
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>) list.get(i);
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			Integer num = (Integer) map.get("num");
			BigDecimal total = BigDecimalUtils.multiply(num, salejg);
			totalAll = totalAll.add(total);
		}
		this.setRootValue("list", list);
		this.setRootValue("total", totalAll);
		
	}

}
