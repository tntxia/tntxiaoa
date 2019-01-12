package com.tntxia.oa.sale.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.web.mvc.WebRuntime;

public class SaleApprovingViewManToAuditAgainAction extends OACommonHandler {
	
	private SaleLightService service = new SaleLightService();

	
	@Override
	public void init(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		
		Map<String,Object> detail = service.getDetail(id);
		
		Boolean fif = ((String) detail.get("fif")).trim().equals("是");
		String item = (String)detail.get("item");
		
		double tax=0;
		
		if(item.equals("增值发票17%")){
			tax=0.17;
		}else if(item.equals("普通发票6%")){
			tax=0.06;
		}else if(item.equals("普通发票4%")){
			tax=0.04;
		}
		
		this.putAllRootValue(detail);
		
		this.setRootValue("fif", fif);
		
		List<SalePro> list = service.getProductList(id);
		BigDecimal zprice=BigDecimal.ZERO;
		for(SalePro pro : list){
			zprice=zprice.add(pro.getTotalPrice());
			pro.calcProfit(tax);
		}
		
		this.setRootValue("zprice", zprice);
		this.setRootValue("productList", list);
		
	}

}
