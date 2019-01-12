package com.tntxia.oa.finance.handler;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class FinanceGatheringViewHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getGathering(String id) throws Exception{
		return dbManager.queryForMap("select  * from gatherview where id=?",new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getProList(String id) throws Exception{
		return dbManager.queryForList("select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc",new Object[]{id}, true);
	}

	@SuppressWarnings("rawtypes")
	private Map<String,BigDecimal> getPriceTotal(List list){
		
		BigDecimal orderTotal = BigDecimal.ZERO;
		BigDecimal outTotal = BigDecimal.ZERO;
		
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			Integer num = (Integer) map.get("num");
			Integer s_num = (Integer) map.get("num");
			orderTotal = orderTotal.add(salejg.multiply(BigDecimal.valueOf(num)));
			outTotal = outTotal.add(salejg.multiply(BigDecimal.valueOf(s_num)));
		}
		
		Map<String,BigDecimal> res = new HashMap<String,BigDecimal>();
		res.put("orderTotal", orderTotal);
		res.put("outTotal", outTotal);
		return res;
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		Map<String,Object> root = this.getRoot();
		String id = runtime.getParam("id");
		
		Map<String,Object> gathering = this.getGathering(id);
		String fyid = (String) gathering.get("fyid");
		List proList = this.getProList(fyid);
		root.putAll(gathering);
		root.put("proList", proList);
		this.setRootValue("id", id);
		this.setRootValue("priceTotal",this.getPriceTotal(proList));
		root.put("r_wlz_add", this.existRight(runtime, "r_wlz_add"));
		

	}

}
